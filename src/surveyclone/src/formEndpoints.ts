/**
 * This contains all of the http functions responsible for handling creating, submitting, and viewing a form.
 * The try catch for all these should be handled in the actual frontend so it can be reacted to in the frontend.
 */

import { Form, Question } from "./types"

export function getId(): string | null {
    const cookies = document.cookie.split('; ');
    for (const cookie of cookies) {
        if (cookie.startsWith("id=")) {
            return decodeURIComponent(cookie.substring(3))
        }
    }
    return null;
}

export function storeId(id: string) {
    document.cookie = `id=${id}`;
}


/**
 * Sends the create form request. Takes a name (of the form)
 * and returns an id (of the form)
 * @param name 
 */
export async function createForm(name: string): Promise<string> {
    const params = new URLSearchParams({
        name: name
    })
    const response = await fetch(`/surveycreation?${params}`, {
        method: 'POST',
    })

    try {
        const data = await response.json()
        if (!response.ok) {
            throw new Error(`Http failure: ${response.status} with message: ${data.message}`)
        }

        if (!data.id) { throw new Error('Error: id is null.') }
        return data.id

    } catch (err) {
        throw new Error("Error parsing json")
    }
}


export class FormRequestError extends Error {
    // The actual return code
    status: number
    // The questions that had the problem.
    questionErrors: Record<number, string>

    constructor(status: number, message: string, questionErrors: Record<number, string> = {}) {
        super(message);
        this.name = 'FormRequestError';
        this.status = status;
        this.questionErrors = questionErrors;
    }
}

//Extracts all the component numbers with errors on them
async function formError(response: Response): Promise<FormRequestError> {
    try {
        const data = await response.json()
        const questionErrors: Record<number, string> = {}

        if (data) {
            for (const [key, value] of Object.entries(data)) {
                if (typeof value === 'string') {
                    questionErrors[Number(key)] = value
                }
            }
        }


        const message = data?.message ?? `Http failure: ${response.status}`
        return new FormRequestError(response.status, message, questionErrors)
    } catch (err) {
        throw new Error("Error parsing json")
    }
}

export async function addToForm(id: string, questions: Question[]): Promise<void> {
    const params = new URLSearchParams({
        id: id
    })

    const response = await fetch(`/surveycreation/${id}/questions`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(questions)
    })

    try {
        const status = await response.status;
        switch (status) {
            case 200:
                break;
            default:
                const data = await response.json()
                throw new Error(`Http failure: ${response.status} with message: ${data.message}`)
                break;
        }
    } catch (err: any) {
        throw new Error("Error parsing json: " + err.message)
    }

}

export async function viewForm(id: string): Promise<Form> {
    const params = new URLSearchParams({ id })
    const response = await fetch(`/viewForm?${params}`)
    const data = await response.json()

    if (!response.ok) throw new Error(`There was a problem viewing this form: ${data.message}`)

    if (!data || !Array.isArray(data.questions)) {
        throw new FormRequestError(response.status, 'Error: Server returned a malformed form.')
    }
    return data as Form
}
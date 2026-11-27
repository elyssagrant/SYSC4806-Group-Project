/**
 * This contains all of the http functions responsible for handling creating, submitting, and viewing a form.
 * The try catch for all these should be handled in the actual frontend so it can be reacted to in the frontend.
 */

import { questionsEqual } from "./surveyComponents/componentTypes";
import { Answer, Form, Question } from "./types"

export function getId(): string | null {
    return localStorage.getItem("id");
}

export function storeId(id: string) {
    localStorage.setItem('id', id);
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
    const response = await fetch(`/survey?${params}`, {
        method: 'POST',
    })

    const data = await response.json().catch(() => ({}));
    if (!response.ok) {
        throw new Error(`${data.message}`)
    }

    if (!data.id) { throw new Error('Error: id is null.') }
    return data.id
}


// This was supposed to allow us to specify specific errors with specific questions submitted when creating the form,
// Not implemented on the backend yet.
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

async function formError(response: Response): Promise<FormRequestError> {
    try {
        const data = await response.json().catch(() => ({}));
        const questionErrors: Record<number, string> = {}

        if (data) {
            for (const [key, value] of Object.entries(data)) {
                if (key !== 'message' && typeof value === 'string') {
                    questionErrors[Number(key)] = value
                }
            }
        }
        const message = data.message;
        return new FormRequestError(response.status, message, questionErrors)
    } catch (err) {
        throw new Error("Error parsing json")
    }
}

export async function saveToForm(id: string, version: number,  questions: Question[], remoteQuestions: Question[]): Promise<void> {
    const questionMap = new Set(questions.map(q => q.id));
    const remoteQuestionMap = new Map<number, Question>(
        remoteQuestions.map(item => [item.id, item])
    );

    // Check for added questions
    const addedQuestions = questions.filter(q => !remoteQuestionMap.has(q.id));
    // Check for edited questions
    const editedQuestions = questions.filter(q => remoteQuestionMap.has(q.id) && !questionsEqual(q, remoteQuestionMap.get(q.id) as Question))
    // Check for deleted questions
    const deletedQuestions = remoteQuestions.filter(q => !questionMap.has(q.id));
    //TODO: Check for swapped questions (potential feature for swapping the order of questions milestone 3)


    const response = await fetch(`/survey/${id}/questions`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            version: version,
            add: addedQuestions,
            edit: editedQuestions,
            delete: deletedQuestions,
        })
    })

    try {
        const status = await response.status;
        switch (status) {
            case 201:
                return;
            // Error with the form fields
            case 422:
                throw await formError(response);
            // This one we use specifically for if someone else's edit has incremented the version number.
            // We can have it use the default or make a new error type to re-fetch the form and either do a reset or a rebase
            //TODO Better version control (milestone 3, optional)
            case 409:
            default:
                const data = await response.json().catch(() => ({}));
                throw new Error(`${data.message}`)
        }
    } catch (err: any) {
        throw new Error(err.message)
    }
}

export async function viewForm(id: string): Promise<Form> {
    const response = await fetch(`/survey/${id}`)
    const data = await response.json().catch(() => ({}));

    if (!response.ok) throw new Error(`There was a problem viewing this form: ${data.message}`)

    if (!data || !Array.isArray(data.questions)) {
        throw new FormRequestError(response.status, 'Error: Server returned a malformed form.')
    }
    return data as Form
}

export async function deleteForm(id: string): Promise<void> {
    const response = await fetch(`/survey/${id}`, {
        method: 'DELETE'
    })

    try {
        const status = await response.status;
        switch (status) {
            case 204:
                break;
            default:
                const data = await response.json().catch(() => ({}));
                throw new Error(`${data.message}`)
        }
    } catch (err: any) {
        throw new Error(err.message || "Unknown error")
    }
}

export async function answerForm(id: string, version: number, answers: Answer[]): Promise<void> {
    let response: Response
    try {
        response = await fetch(`/survey/${id}`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                version: version,
                responses: answers
            })
        })
    } catch (error: any) {
        throw Error("There was a problem posting the answer to the server");
    }

    try {
        const status = response.status;
        switch (status) {
            case 202:
                break;
            default:
                const data = await response.json().catch(() => ({}));
                throw new Error(`${data.message}`);
        }

    } catch (err: any) {
        throw new Error(err.message || "Unknown error")
    }
}

/**
 * This is a request to check if a user is logged in or not
 */
export async function loggedIn(): Promise<boolean> {
    const response = await fetch(`/user/auth`, {
        method: 'GET'
    })

    try {
        const status = response.status;
        const result = await response.json();
        switch (status) {
            case 200:
                return result;
            default:
                return false;
        }
    } catch(error: any) {
        throw new Error(error.message || "Unknown error")
    }
}
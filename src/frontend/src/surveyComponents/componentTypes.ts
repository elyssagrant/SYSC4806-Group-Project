/**
 * Argument types that are shared between survey components.
 * Needed to tell whether they are being rendered in edit or answer mode.
 */
import { Answer, MultipleChoiceQuestion, NumberRangeQuestion, Question } from '../types'

export type Response = string | number

// Contains a question, and then which mode it is in. If answer, contain the answer
export type FieldProps<Q extends Question> = { mode: 'edit'; question: Q; onChange: (question: Q) => void } | { mode: 'answer'; question: Q; value?: Response; onAnswer: (value: Response) => void }

// Options in the dropdown
export const QUESTION_TYPES: {
    value: Question['type'],
    label: string
}[] = [
        { value: 'Text', label: 'Text' },
        { value: 'NumberRange', label: 'Number range' },
        { value: 'MultipleChoice', label: 'Multiple choice' },
    ]

// Return the default values for a given question
let tempId = -1;
export function newQuestion(type: Question['type'], index: number): Question {
    const id = tempId--;
    switch (type) {
        case 'Text':
            return { type, id: id, questionText: '' };
        case 'NumberRange':
            return { type, id: id, questionText: '', lowerBound: 0, upperBound: 5 };
        case 'MultipleChoice':
            return { type, id: id, questionText: '', choices: ['', ''] };
    }
}

// Call this function on every question when sending - null means success
export function checkQuestion(question: Question): string | null {
    if (!question.questionText) return "No question defined."

    switch (question.type) {
        case 'Text':
            // no checks needed
            return null;
        case 'NumberRange':
            if (Number.isNaN(question.lowerBound) || Number.isNaN(question.upperBound)) {
                return 'One or more bounds provided were not a number';
            }
            if (question.lowerBound >= question.upperBound) {
                return 'Lower bound must be less than upper bound';
            }
            return null;
        case 'MultipleChoice':
            if (question.choices.length < 2) return 'There must be at least 2 choices';
            return null;
    }
}

// Checks two questions to see if they are equal (for detecting edits)
export function questionsEqual(a: Question, b: Question): boolean {
    if (a.type !== b.type || a.questionText !== b.questionText) return false;

    switch (a.type) {
        case 'Text':
            return true;

        case 'NumberRange': {
            const q = b as NumberRangeQuestion;
            return a.lowerBound === q.lowerBound && a.upperBound === q.upperBound;
        }

        case 'MultipleChoice': {
            const q = b as MultipleChoiceQuestion;
            return a.choices.length === q.choices.length
                && a.choices.every((c, i) => c === q.choices[i]);
        }
    }
}

// Checks a given answer to see if it matches with it's corresponding question
export function checkAnswer(q: Question, a: Answer): string | null {
    if (a.type !== q.type) return "Question and Answer types do not correspond";

    switch (a.type) {
        case 'Text': {
            if (a.response == null) {
                return "Response is null";
            }
            break;
        }
        case 'MultipleChoice': {
            if (a.response == null) {
                return "Response is null";
            }
            const mc = (q as MultipleChoiceQuestion)
            if (mc.choices.length >= (a.response as number)) {
                return "Response does not exist";
            }
            break;
        }
        case 'NumberRange': {
            if (a.response == null) {
                return "Response is null";
            }
            const nr = (q as NumberRangeQuestion);
            if ((a.response as number) < nr.lowerBound || (a.response as number) > nr.upperBound) {
                return "Response is out of bounds";
            }
            break;
        }
        default: return "Response type does not exist";
    }

    return null;
}

// Function to convert multiple choice string answers to their index
// Note - the errors in this function should never be thrown. There are question specific checks for this before.
export function convertMCStringToMCIndex(questions: Question[], answers: Answer[]): void {
    if (questions.length !== answers.length) {throw new Error("Questions and Answers lengths are not equal.");}
    answers.forEach((a, i) => {
        if (a.type === 'MultipleChoice') {
            const index = (questions[i] as MultipleChoiceQuestion).choices.indexOf((a.response as string))
            if (index < 0) {
                throw new Error("A multiple choice answer that does not exist has been selected.");
            }
            a.response = index; // Set the answer to index of the choice instead of the string response
        }
    });
}

// Function to convert multiple choice index answers to string answers
// This is so that the form doesn't have an invalid answer after pressing submit,
// It may come in handy when looking an answers
export function convertMCIndexToMCString(questions: Question[], answers: Answer[]): void {
    if (questions.length !== answers.length) { throw new Error("Questions and Answers lengths are not equal."); }
    answers.forEach((a, i) => {
        if (a.type === 'MultipleChoice') {
            try {
                const choice = (questions[i] as MultipleChoiceQuestion).choices[a.response as number]
                a.response = choice;// Set the answer to string of the choice instead of the index response
            } catch (error: any) {
                throw new Error("A multiple choice answer that does not exist has been selected.");
            }
        }
    });
}
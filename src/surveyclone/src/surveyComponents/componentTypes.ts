/**
 * Argument types that are shared between survey components.
 * Needed to tell whether they are being rendered in edit or answer mode.
 */
import { MultipleChoiceQuestion, NumberRangeQuestion, Question } from '../types'

export type Answer = string | number

// Contains a question, and then which mode it is in. If answer, contain the answer
export type FieldProps<Q extends Question> = { mode: 'edit'; question: Q; onChange: (question: Q) => void } | { mode: 'answer'; question: Q; value?: Answer; onAnswer: (value: Answer) => void }

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
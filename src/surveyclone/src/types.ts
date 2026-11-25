import MultipleChoiceField from './surveyComponents/MultipleChoiceField';
import TextField from './surveyComponents/TextField';
import NumberRangeField from './surveyComponents/NumberRangeField';
/**
 * This files contains a bunch of types that are used by the frontend to render and send form information
 * These types mimic the schema.txt I wrote and should be casted back and forth
 * *On making a new question type, it should also be added here.
 */

export interface TextQuestion {
    type: 'Text'
    id: number
    questionText: string
}

export interface NumberRangeQuestion {
    type: 'NumberRange'
    id: number
    questionText: string
    lowerBound: number
    upperBound: number
}

export interface MultipleChoiceQuestion {
    type: 'MultipleChoice'
    id: number
    questionText: string
    choices: string[]
}

export type Question = MultipleChoiceQuestion | NumberRangeQuestion | TextQuestion;

export interface Form {
    name: string
    version: number
    questions: Question[]
}

export interface Result {
    questionId: number;
    type: string;
    questionText: string;
    chart: string;
    textResponses: string[];
}
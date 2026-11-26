import {Result} from './types'

export async function getMetrics(id: string): Promise<Result[]> {
    const response = await fetch(
        `http://localhost:8080/survey/${id}/metrics`
    );

    if (!response.ok) {
        throw new Error("Unable to retrieve survey metrics.");
    }

    return await response.json();
}

export function getId(result: Result): number {
    return result.questionId;
}

export function getChart(result: Result): string {
    return result.chart;
}

export function isNumberRange(result: Result): boolean {
    return (result.type === "NumberRange");
}

export function isMultipleChoice(result: Result): boolean {
    return (result.type === "MultipleChoice");
}

export function isText(result: Result): boolean {
    return (result.type === "Text");
}

export function getTextResponses(result: Result): string[] {
    return result.textResponses;
}

export function getQuestionText(result: Result): string {
    return result.questionText;
}
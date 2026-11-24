export async function getMetrics(id: string): Promise<string[]> {
    const response = await fetch(
        `http://localhost:8080/survey/${id}/metrics`
    );

    if (!response.ok) {
        throw new Error("Unable to retrieve survey metrics.");
    }

    return await response.json();
}
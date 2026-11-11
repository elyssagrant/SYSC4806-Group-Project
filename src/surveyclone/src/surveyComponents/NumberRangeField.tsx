import React, { useState } from "react";
import { Range } from "react-range";
import { FieldProps } from "./componentTypes";
import { NumberRangeQuestion } from "../types";

export default function NumberRangeField(props: FieldProps<NumberRangeQuestion>) {
    const { question } = props;
    const { lowerBound, upperBound } = question;
    const validRange = upperBound > lowerBound;

    if (props.mode == 'edit') {
        return (
            <div className="formField">
                <label className="questionInput">
                    <input
                        type="text"
                        value={question.questionText}
                        placeholder="Question goes here"
                        onChange={e => props.onChange({ ...question, questionText: e.target.value })}
                    />
                </label>
                <div className="formRow">
                    <label className="optionLabel">
                        <input
                            type="number"
                            value={lowerBound}
                            onChange={e => props.onChange({ ...question, lowerBound: e.target.valueAsNumber })}
                        />
                    </label>
                </div>
                <div className="formRow">
                    <label className="optionLabel">
                        <input
                            type="number"
                            value={upperBound}
                            onChange={e => props.onChange({ ...question, upperBound: e.target.valueAsNumber })}
                        />
                    </label>
                </div>
            </div>
        )
    } else {
        const value = typeof props.value === 'number' ? props.value : lowerBound

        return (
            <div className="formField">
                <p className="questionLabel">{question.questionText}</p>
                <div className="numberRange">
                    <Range
                        step={1}
                        min={lowerBound}
                        max={upperBound}
                        values={[value]}
                        onChange={(newValues) => props.onAnswer(newValues[0])}
                        renderTrack={({ props, children }) => (
                            <div {...props} style={{ ...props.style, height: "6px", backgroundColor: "#ccc" }}>
                            {children}
                            </div>
                        )}
                        renderThumb={({ props }) => (
                            <div {...props} style={{ ...props.style, height: "24px", width: "24px", backgroundColor: "#999" }} />
                        )}
                    
                    />
                </div>
            </div>
        )
    }
}
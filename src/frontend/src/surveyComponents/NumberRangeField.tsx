import { FieldProps } from "./componentTypes";
import { NumberRangeQuestion } from "../types";
import { useState } from "react";

export default function NumberRangeField(props: FieldProps<NumberRangeQuestion>) {
    const { question } = props;
    const { lowerBound, upperBound } = question;
    const [sliderValue, setSliderValue] = useState("" + Math.floor((upperBound + lowerBound)/2));

    if (props.mode === 'edit') {
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
        const sliderChange = (value: string) => {
            setSliderValue(value)
            props.onAnswer(value);
        }

        return (
            <div className="formField">
                <p className="questionLabel">{question.questionText}</p>
                <div className="numberRange">
                    <label>
                        <span className="label-min">{lowerBound}</span>
                        <input
                            type="range"
                            min={lowerBound}
                            max={upperBound}
                            step={1}
                            value={sliderValue}
                            onChange={(newValues) => sliderChange(newValues.target.value)}
                        />
                        <span className="label-max">{upperBound}</span>
                        <div className="slider-value-display">{sliderValue}</div>
                    </label>
                </div>
            </div>
        )
    }
}
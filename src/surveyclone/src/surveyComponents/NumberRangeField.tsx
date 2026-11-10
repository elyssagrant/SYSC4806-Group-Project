import React, { useState } from "react";
import { Range } from "react-range";
import { FieldProps } from "./QuestionField";
import { NumberRangeQuestion } from "../types";

export default function NumberRangeField(props: FieldProps<NumberRangeQuestion>) {
    const { question } = props;
    const { lowerBound, upperBound } = question;
    const validRange = upperBound > lowerBound;

    if (props.mode == 'edit') {
        return (
            <div className="formField"></div>
        )
    } else {

    }
    
}
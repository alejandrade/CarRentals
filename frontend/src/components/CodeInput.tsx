import React from "react";
import { Button, TextField } from "@mui/material";

interface CodeInputProps {
    onSubmit: (code: string) => void;
}

const CodeInput: React.FC<CodeInputProps> = ({  onSubmit }) => {
    const [value, setValue] = React.useState("");

    const handleChange = (event: any) => {
        setValue(event.target.value);
    };

    const handleSubmit = (event: any) => {
        event.preventDefault();
        onSubmit(value);
    };

    return (
        <div>
            <TextField
                id="code"
                label="Code"
                type="number"
                value={value}
                onChange={handleChange}
                required
            />
            <Button
                variant="contained"
                color="primary"
                onClick={handleSubmit}
            >
                Submit
            </Button>
        </div>
    );
};

export default CodeInput;

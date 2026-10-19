package com.healthsystem.xml.validator;

public class ValidationError {

    public enum Severity { WARNING, ERROR, FATAL_ERROR }

    private final Severity severity;
    private final int lineNumber;
    private final int columnNumber;
    private final String message;

    public ValidationError(Severity severity, int lineNumber, int columnNumber, String message) {
        this.severity = severity;
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
        this.message = message;
    }

    public Severity getSeverity() { return severity; }
    public int getLineNumber() { return lineNumber; }
    public int getColumnNumber() { return columnNumber; }
    public String getMessage() { return message; }

    @Override
    public String toString() {
        return String.format("[%s] Line %d, Col %d: %s", severity, lineNumber, columnNumber, message);
    }
}

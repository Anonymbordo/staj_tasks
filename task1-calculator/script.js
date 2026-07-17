const display = document.querySelector("#display");
const expressionView = document.querySelector("#expression");
const historyPanel = document.querySelector("#historyPanel");
const keys = document.querySelector(".keys");
const memoryRow = document.querySelector(".memory-row");
const angleButton = document.querySelector("[data-action='toggle-angle']");
const historyButton = document.querySelector("[data-action='toggle-history']");

let expression = "";
let lastAnswer = 0;
let memory = 0;
let angleMode = "DEG";
let justCalculated = false;
let errorMessage = "";
let history = [];

keys.addEventListener("click", handleButtonClick);
memoryRow.addEventListener("click", handleButtonClick);
angleButton.addEventListener("click", handleButtonClick);
historyButton.addEventListener("click", handleButtonClick);
document.addEventListener("keydown", handleKeyboard);

render();

function handleButtonClick(event) {
    const button = event.target.closest("button");

    if (!button) {
        return;
    }

    if (button.dataset.insert) {
        insertToken(button.dataset.insert);
    } else if (button.dataset.function) {
        insertFunction(button.dataset.function);
    } else {
        runAction(button.dataset.action);
    }

    render();
}

function handleKeyboard(event) {
    const key = event.key;

    if (/^[0-9]$/.test(key)) {
        insertToken(key);
    } else if (["+", "-", "*", "/", "^", "(", ")"].includes(key)) {
        insertToken(key);
    } else if (key === "." || key === ",") {
        insertDecimal();
    } else if (key === "Enter" || key === "=") {
        event.preventDefault();
        calculateResult();
    } else if (key === "Backspace") {
        backspace();
    } else if (key === "Escape") {
        clearAll();
    } else {
        return;
    }

    render();
}

function insertToken(token) {
    errorMessage = "";

    if (justCalculated && /[0-9.(pie]/.test(token)) {
        expression = "";
    }

    if (token === "pi" || token === "e") {
        expression += needsMultiplicationBeforeConstant() ? `*${token}` : token;
    } else {
        expression += token;
    }

    justCalculated = false;
}

function insertFunction(name) {
    errorMessage = "";

    if (justCalculated) {
        expression = "";
    }

    expression += `${name}(`;
    justCalculated = false;
}

function insertDecimal() {
    errorMessage = "";
    const currentNumber = expression.match(/(\d+\.?\d*|\.\d*)$/)?.[0] || "";

    if (!currentNumber.includes(".")) {
        expression += currentNumber ? "." : "0.";
    }

    justCalculated = false;
}

function runAction(action) {
    switch (action) {
        case "clear":
            clearAll();
            break;
        case "backspace":
            backspace();
            break;
        case "percent":
            wrapLastValue("/100");
            break;
        case "square":
            wrapLastValue("^2");
            break;
        case "inverse":
            wrapLastValue("^-1");
            break;
        case "decimal":
            insertDecimal();
            break;
        case "equals":
            calculateResult();
            break;
        case "toggle-angle":
            angleMode = angleMode === "DEG" ? "RAD" : "DEG";
            break;
        case "toggle-history":
            historyPanel.classList.toggle("is-visible");
            break;
        case "memory-clear":
            memory = 0;
            break;
        case "memory-recall":
            insertToken(formatNumber(memory));
            break;
        case "memory-add":
            memory += currentNumericValue();
            break;
        case "memory-subtract":
            memory -= currentNumericValue();
            break;
        case "answer":
            insertToken(formatNumber(lastAnswer));
            break;
        default:
            break;
    }
}

function clearAll() {
    errorMessage = "";
    expression = "";
    justCalculated = false;
}

function backspace() {
    errorMessage = "";

    if (justCalculated) {
        clearAll();
        return;
    }

    expression = expression.slice(0, -1);
}

function wrapLastValue(suffix) {
    errorMessage = "";
    const match = expression.match(/(pi|e|\d+(?:\.\d+)?|\.\d+|\))$/);

    if (!match) {
        return;
    }

    expression += suffix;
    justCalculated = false;
}

function calculateResult() {
    errorMessage = "";

    if (!expression) {
        return;
    }

    try {
        const original = expression;
        const result = evaluateExpression(expression);
        const formatted = formatNumber(result);

        expression = formatted;
        lastAnswer = result;
        justCalculated = true;
        addHistory(original, formatted);
    } catch (error) {
        errorMessage = "Hata";
        justCalculated = true;
    }
}

function currentNumericValue() {
    if (!expression) {
        return 0;
    }

    try {
        return evaluateExpression(expression);
    } catch (error) {
        return lastAnswer || 0;
    }
}

function addHistory(input, result) {
    history.unshift(`${toDisplayExpression(input)} = ${result.replace(".", ",")}`);
    history = history.slice(0, 8);
    historyPanel.innerHTML = history.map((item) => `<div class="history-item">${item}</div>`).join("");
}

function render() {
    angleButton.textContent = angleMode;

    if (errorMessage) {
        expressionView.textContent = expression ? toDisplayExpression(expression) : "Hazir";
        display.textContent = errorMessage;
        return;
    }

    expressionView.textContent = expression ? toDisplayExpression(expression) : "Hazir";

    if (!expression) {
        display.textContent = "0";
        return;
    }

    if (justCalculated) {
        display.textContent = expression.replace(".", ",");
        return;
    }

    try {
        display.textContent = formatNumber(evaluateExpression(expression)).replace(".", ",");
    } catch (error) {
        display.textContent = toDisplayExpression(expression).slice(-14);
    }
}

function toDisplayExpression(value) {
    return value
        .replaceAll("*", "×")
        .replaceAll("/", "÷")
        .replaceAll("-", "−")
        .replaceAll("pi", "π")
        .replaceAll("sqrt", "√")
        .replaceAll(".", ",");
}

function needsMultiplicationBeforeConstant() {
    return /[0-9)]$/.test(expression);
}

function formatNumber(number) {
    if (!Number.isFinite(number)) {
        throw new Error("Invalid number");
    }

    const rounded = Number.parseFloat(number.toPrecision(12));
    return Object.is(rounded, -0) ? "0" : rounded.toString();
}

function evaluateExpression(input) {
    const tokens = tokenize(input);
    const output = [];
    const operators = [];
    const precedence = { "+": 1, "-": 1, "*": 2, "/": 2, "^": 3, "u-": 4 };
    const rightAssociative = new Set(["^", "u-"]);

    tokens.forEach((token, index) => {
        if (token.type === "number" || token.type === "constant") {
            output.push(token);
            return;
        }

        if (token.type === "function") {
            operators.push(token);
            return;
        }

        if (token.value === "(") {
            operators.push(token);
            return;
        }

        if (token.value === ")") {
            while (operators.length && operators[operators.length - 1].value !== "(") {
                output.push(operators.pop());
            }

            if (!operators.length) {
                throw new Error("Missing parenthesis");
            }

            operators.pop();

            if (operators.length && operators[operators.length - 1].type === "function") {
                output.push(operators.pop());
            }

            return;
        }

        if (token.type === "operator") {
            const previous = tokens[index - 1];
            let operator = token.value;

            if (operator === "-" && (!previous || previous.type === "operator" || previous.value === "(")) {
                operator = "u-";
            }

            while (operators.length) {
                const top = operators[operators.length - 1];

                if (top.type === "function") {
                    output.push(operators.pop());
                    continue;
                }

                if (top.value === "(") {
                    break;
                }

                const shouldPop = rightAssociative.has(operator)
                    ? precedence[operator] < precedence[top.value]
                    : precedence[operator] <= precedence[top.value];

                if (!shouldPop) {
                    break;
                }

                output.push(operators.pop());
            }

            operators.push({ type: "operator", value: operator });
        }
    });

    while (operators.length) {
        const operator = operators.pop();

        if (operator.value === "(" || operator.value === ")") {
            throw new Error("Missing parenthesis");
        }

        output.push(operator);
    }

    return runRpn(output);
}

function tokenize(input) {
    const tokens = [];
    let index = 0;

    while (index < input.length) {
        const char = input[index];

        if (/\s/.test(char)) {
            index += 1;
        } else if (/\d|\./.test(char)) {
            const match = input.slice(index).match(/^\d*\.?\d+/)[0];
            tokens.push({ type: "number", value: Number(match) });
            index += match.length;
        } else if (["+", "-", "*", "/", "^"].includes(char)) {
            tokens.push({ type: "operator", value: char });
            index += 1;
        } else if (char === "(" || char === ")") {
            tokens.push({ type: "parenthesis", value: char });
            index += 1;
        } else {
            const match = input.slice(index).match(/^[a-z]+/i);

            if (!match) {
                throw new Error("Unknown token");
            }

            const word = match[0];

            if (word === "pi" || word === "e") {
                tokens.push({ type: "constant", value: word });
            } else if (["sin", "cos", "tan", "ln", "log", "sqrt"].includes(word)) {
                tokens.push({ type: "function", value: word });
            } else {
                throw new Error("Unknown function");
            }

            index += word.length;
        }
    }

    return addImplicitMultiplication(tokens);
}

function addImplicitMultiplication(tokens) {
    const result = [];

    tokens.forEach((token, index) => {
        const previous = tokens[index - 1];

        if (previous && shouldInsertMultiplication(previous, token)) {
            result.push({ type: "operator", value: "*" });
        }

        result.push(token);
    });

    return result;
}

function shouldInsertMultiplication(previous, current) {
    const previousCanMultiply = previous.type === "number" || previous.type === "constant" || previous.value === ")";
    const currentCanMultiply = current.type === "number" || current.type === "constant" || current.type === "function" || current.value === "(";
    return previousCanMultiply && currentCanMultiply;
}

function runRpn(tokens) {
    const stack = [];

    tokens.forEach((token) => {
        if (token.type === "number") {
            stack.push(token.value);
        } else if (token.type === "constant") {
            stack.push(token.value === "pi" ? Math.PI : Math.E);
        } else if (token.type === "function") {
            const value = stack.pop();
            stack.push(runFunction(token.value, value));
        } else if (token.type === "operator") {
            if (token.value === "u-") {
                stack.push(-stack.pop());
                return;
            }

            const second = stack.pop();
            const first = stack.pop();
            stack.push(runOperator(first, second, token.value));
        }
    });

    if (stack.length !== 1 || stack[0] === undefined) {
        throw new Error("Invalid expression");
    }

    return stack[0];
}

function runOperator(first, second, operator) {
    if (first === undefined || second === undefined) {
        throw new Error("Missing operand");
    }

    switch (operator) {
        case "+":
            return first + second;
        case "-":
            return first - second;
        case "*":
            return first * second;
        case "/":
            if (second === 0) {
                throw new Error("Division by zero");
            }
            return first / second;
        case "^":
            return Math.pow(first, second);
        default:
            throw new Error("Unknown operator");
    }
}

function runFunction(name, value) {
    if (value === undefined) {
        throw new Error("Missing value");
    }

    const angleValue = angleMode === "DEG" ? value * Math.PI / 180 : value;

    switch (name) {
        case "sin":
            return Math.sin(angleValue);
        case "cos":
            return Math.cos(angleValue);
        case "tan":
            return Math.tan(angleValue);
        case "ln":
            if (value <= 0) {
                throw new Error("Invalid ln");
            }
            return Math.log(value);
        case "log":
            if (value <= 0) {
                throw new Error("Invalid log");
            }
            return Math.log10(value);
        case "sqrt":
            if (value < 0) {
                throw new Error("Invalid sqrt");
            }
            return Math.sqrt(value);
        default:
            throw new Error("Unknown function");
    }
}

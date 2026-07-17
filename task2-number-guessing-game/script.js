const scoreValue = document.querySelector("#scoreValue");
const timeValue = document.querySelector("#timeValue");
const attemptValue = document.querySelector("#attemptValue");
const modeButtons = document.querySelectorAll(".mode-button");
const guessMode = document.querySelector("#guessMode");
const parkourMode = document.querySelector("#parkourMode");

const rangeSelect = document.querySelector("#rangeSelect");
const rangeEnd = document.querySelector("#rangeEnd");
const guessInput = document.querySelector("#guessInput");
const guessButton = document.querySelector("#guessButton");
const startGuessButton = document.querySelector("#startGuessButton");
const guessStatus = document.querySelector("#guessStatus");
const guessResult = document.querySelector("#guessResult");

const runner = document.querySelector("#runner");
const parkourLevel = document.querySelector("#parkourLevel");
const mathQuestion = document.querySelector("#mathQuestion");
const answerInput = document.querySelector("#answerInput");
const answerButton = document.querySelector("#answerButton");
const startParkourButton = document.querySelector("#startParkourButton");
const parkourStatus = document.querySelector("#parkourStatus");

const guessState = { secret: 0, max: 100, attempts: 7, time: 60, score: 0, active: false, timer: null };
const parkourState = { score: 0, time: 75, level: 1, maxLevel: 8, answer: 0, active: false, timer: null };

let activeMode = "guess";

modeButtons.forEach((button) => button.addEventListener("click", () => switchMode(button.dataset.mode)));

rangeSelect.addEventListener("change", () => {
    const max = Number(rangeSelect.value);
    rangeEnd.textContent = max;
    guessInput.max = max;
    if (!guessState.active) {
        configureGuess(max);
        renderGuessStats();
    }
});

guessButton.addEventListener("click", submitGuess);
startGuessButton.addEventListener("click", startGuessGame);
guessInput.addEventListener("keydown", (event) => {
    if (event.key === "Enter") submitGuess();
});

answerButton.addEventListener("click", submitParkourAnswer);
startParkourButton.addEventListener("click", startParkourGame);
answerInput.addEventListener("keydown", (event) => {
    if (event.key === "Enter") submitParkourAnswer();
});

configureGuess(100);
renderGuessStats();

function switchMode(mode) {
    activeMode = mode;
    modeButtons.forEach((button) => {
        const isActive = button.dataset.mode === mode;
        button.classList.toggle("is-active", isActive);
        button.setAttribute("aria-selected", String(isActive));
    });
    guessMode.classList.toggle("is-active", mode === "guess");
    parkourMode.classList.toggle("is-active", mode === "parkour");
    if (mode === "guess") renderGuessStats(); else renderParkourStats();
}

function configureGuess(max) {
    guessState.max = max;
    guessState.attempts = max === 100 ? 7 : 10;
    guessState.time = max === 100 ? 60 : 90;
}

function startGuessGame() {
    stopTimer(guessState);
    configureGuess(Number(rangeSelect.value));
    guessState.secret = randomInt(1, guessState.max);
    guessState.score = 0;
    guessState.active = true;
    guessInput.value = "";
    guessStatus.textContent = `1 ile ${guessState.max} arasında bir sayı tuttum.`;
    guessResult.textContent = "Tahmin et";
    guessInput.focus();
    renderGuessStats();
    guessState.timer = setInterval(() => {
        guessState.time -= 1;
        renderGuessStats();
        if (guessState.time <= 0) finishGuess(false, "Süre bitti");
    }, 1000);
}

function submitGuess() {
    if (!guessState.active) {
        startGuessGame();
        return;
    }
    const guess = Number(guessInput.value);
    if (!Number.isInteger(guess) || guess < 1 || guess > guessState.max) {
        guessStatus.textContent = `Lütfen 1 ile ${guessState.max} arasında bir tam sayı gir.`;
        return;
    }
    guessState.attempts -= 1;
    if (guess === guessState.secret) {
        guessState.score += guessState.attempts * 10 + guessState.time;
        finishGuess(true, "Doğru tahmin");
    } else if (guessState.attempts <= 0) {
        finishGuess(false, guess > guessState.secret ? "Çok yüksek" : "Çok düşük");
    } else if (guess > guessState.secret) {
        guessResult.textContent = "Çok Yüksek";
        guessStatus.textContent = "Biraz aşağı in. Sayı daha küçük.";
    } else {
        guessResult.textContent = "Çok Düşük";
        guessStatus.textContent = "Biraz yukarı çık. Sayı daha büyük.";
    }
    guessInput.value = "";
    renderGuessStats();
}

function finishGuess(won, reason) {
    stopTimer(guessState);
    guessState.active = false;
    guessResult.textContent = won ? "Kazandın" : "Kaybettin";
    guessStatus.textContent = `${reason}. Gerçek sayı ${guessState.secret}.`;
    renderGuessStats();
}

function startParkourGame() {
    stopTimer(parkourState);
    parkourState.score = 0;
    parkourState.time = 75;
    parkourState.level = 1;
    parkourState.active = true;
    answerInput.value = "";
    parkourStatus.textContent = "Doğru cevap ver, koşucu sıradaki platforma atlasın.";
    runner.style.setProperty("--progress", "0%");
    createQuestion();
    answerInput.focus();
    renderParkourStats();
    parkourState.timer = setInterval(() => {
        parkourState.time -= 1;
        renderParkourStats();
        if (parkourState.time <= 0) finishParkour(false, "Süre bitti. Parkur tamamlanamadı.");
    }, 1000);
}

function createQuestion() {
    const operations = ["+", "-", "*", "/"];
    const operation = operations[randomInt(0, operations.length - 1)];
    let first = randomInt(parkourState.level + 3, parkourState.level * 7 + 12);
    let second = randomInt(2, parkourState.level + 9);
    if (operation === "/") {
        const answer = randomInt(2, parkourState.level + 8);
        second = randomInt(2, 10);
        first = answer * second;
        parkourState.answer = answer;
    } else if (operation === "*") {
        parkourState.answer = first * second;
    } else if (operation === "-") {
        if (second > first) [first, second] = [second, first];
        parkourState.answer = first - second;
    } else {
        parkourState.answer = first + second;
    }
    const symbol = operation === "*" ? "×" : operation === "/" ? "÷" : operation;
    mathQuestion.textContent = `${first} ${symbol} ${second} = ?`;
    parkourLevel.textContent = `Engel ${parkourState.level} / ${parkourState.maxLevel}`;
}

function submitParkourAnswer() {
    if (!parkourState.active) {
        startParkourGame();
        return;
    }
    const answer = Number(answerInput.value);
    if (!Number.isInteger(answer)) {
        parkourStatus.textContent = "Cevap tam sayı olmalı.";
        return;
    }
    if (answer !== parkourState.answer) {
        parkourState.time = Math.max(0, parkourState.time - 5);
        parkourStatus.textContent = "Yanlış cevap. 5 saniye kaybettin.";
        answerInput.select();
        renderParkourStats();
        return;
    }
    parkourState.score += 100 + parkourState.time;
    jumpRunner();
    if (parkourState.level >= parkourState.maxLevel) {
        finishParkour(true, "Parkuru tamamladın.");
        return;
    }
    parkourState.level += 1;
    answerInput.value = "";
    parkourStatus.textContent = "Doğru. Sıradaki engel geldi.";
    createQuestion();
    renderParkourStats();
}

function jumpRunner() {
    const progress = Math.round((parkourState.level / parkourState.maxLevel) * 100);
    runner.classList.add("is-jumping");
    runner.style.setProperty("--progress", `${progress}%`);
    window.setTimeout(() => runner.classList.remove("is-jumping"), 240);
}

function finishParkour(won, message) {
    stopTimer(parkourState);
    parkourState.active = false;
    mathQuestion.textContent = won ? "Bitiş" : "Durdu";
    parkourStatus.textContent = `${message} Skor: ${parkourState.score}`;
    answerInput.value = "";
    renderParkourStats();
}

function renderGuessStats() {
    if (activeMode !== "guess") return;
    scoreValue.textContent = guessState.score;
    timeValue.textContent = guessState.time;
    attemptValue.textContent = guessState.attempts;
}

function renderParkourStats() {
    if (activeMode !== "parkour") return;
    scoreValue.textContent = parkourState.score;
    timeValue.textContent = parkourState.time;
    attemptValue.textContent = `${parkourState.level}/${parkourState.maxLevel}`;
}

function stopTimer(state) {
    if (state.timer) {
        clearInterval(state.timer);
        state.timer = null;
    }
}

function randomInt(min, max) {
    return Math.floor(Math.random() * (max - min + 1)) + min;
}

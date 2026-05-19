# Budget Tracker – Personal Finance Console App
**Author:** Kaiya Cooper  

A Java‑based command‑line tool that helps you set monthly budgets for five core categories, record discretionary spendings, and receive instant feedback on how close you’re getting to each budget limit.

---

## Table of Contents
- [Project Overview](#project-overview)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Building & Running](#building--running)
- [Usage](#usage)
- [Extending the App](#extending-the-app)
- [License](#license)

---

## Project Overview
`cooper_kaiya_budgettracker.java` is a single‑source‑file Java program that demonstrates:
- **Array‑based state management** for categories, budgets, and transactions.
- **Console–based user interaction** with a menu to add transactions, view a summary, list all transactions, and generate per‑category reports.
- **Automatic warnings** when spending approaches or exceeds the allotted budget.

The app runs entirely in the terminal – no GUI or external dependencies.

---

## Features
| Feature | Description |
|---------|-------------|
| **Budget Setup** | Prompt user to enter a monthly amount for **Housing, Food, Transportation, Entertainment, Utilities**. |
| **Add Transaction** | Attach a dollar amount and a brief description to any category, with validation for positive amounts and category bounds. |
| **Budget Summary** | View each category’s budget, spending, remaining amount, and status (“ON TRACK”, “NEAR LIMIT”, “OVER BUDGET”). |
| **Transaction List** | Print all recorded transactions with their category, amount, and description. |
| **Category Report** | Get a detailed report on a single category, including cumulative totals and all related transactions. |
| **Maximum Transaction Cap** | Safeguards against exceeding 50 stored transactions. |
| **Console Styling** | Visually organized tables and ANSI blockquotes for clarity. |

---

## Prerequisites
- **Java JDK 17+** (the code uses only core language features)
- A terminal or command prompt

---

## Building & Running

```bash
# Compile
javac cooper_kaiya_budgettracker.java

# Run
java cooper_kaiya_budgettracker
```

You’ll be greeted by a menu:

```
-------------------------------------------------------
|         Personal Budget and Expense Tracker         |
-------------------------------------------------------
```

Navigate using numbers 1–5 to access the functionalities described above.

---

## Usage

1. **Initialize Budgets** – On first launch the program asks for monthly limits for each category.
2. **Add Transactions** – Select “1” from the main menu to record an expense.
3. **View Summaries** – “2” shows the current state of all budgets; “3” lists every transaction; “4” generates a focused report for a chosen category.
4. **Exit** – Choose “5” to finish the session.

The application remains active until you exit, and each step includes clear prompts and simple validation to prevent common entry errors.

---

## Extending the App

| What to change | How |
|----------------|-----|
| **Number of categories** | Adjust `NUM_CATEGORIES`, add names to `categoryNames`, and size the arrays accordingly. |
| **Maximum transactions** | Update `MAX_TRANSACTIONS` and related logic in `addTransaction()`. |
| **Persistency** | Introduce file I/O to read/write budgets and transactions, enabling data to survive between runs. |
| **GUI** | Replace console prompts with Swing or JavaFX for a richer user interface. |

The code is deliberately straightforward, making it a good sandbox for learning Java, data structures, or software design patterns.

---

## License
MIT License – feel free to copy, modify, and distribute.  
(See `LICENSE` file in the repo for full wording.)

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

class Expense {
    private String category;
    private double amount;

    public Expense(String category, double amount) {
        this.category = category;
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }
}

class PersonalFinanceManager {
    private double income;
    private double budget;
    private double savingsGoal;
    private List<Expense> expenses;

    public PersonalFinanceManager(double income, double budget, double savingsGoal) {
        if (budget > income) {
            JOptionPane.showMessageDialog(null, "Enter a valid budget (should be less than or equal to income).");
        }
        this.income = income;
        this.budget = budget;
        this.savingsGoal = savingsGoal;
        this.expenses = new ArrayList<>();
    }

    public void addExpense(String category, double amount) {
        Expense expense = new Expense(category, amount);
        expenses.add(expense);
    }

    public double calculateTotalExpenses() {
        double totalExpenses = 0.0;
        for (Expense expense : expenses) {
            totalExpenses += expense.getAmount();
        }
        return totalExpenses;
    }

    public double calculateRemainingBudget() {
        double totalExpenses = calculateTotalExpenses();
        return budget - totalExpenses;
    }

    public double calculateSavingsProgress() {
        double totalExpenses = calculateTotalExpenses();
        return income - totalExpenses;
    }

    public boolean isSavingsGoalReached() {
        return calculateSavingsProgress() >= savingsGoal;
    }

    public double getIncome() {
        return income;
    }

    public double getBudget() {
        return budget;
    }

    public double getSavingsGoal() {
        return savingsGoal;
    }
}

public class FinanceManagerGUI extends JFrame {
    private JTextField txtIncome;
    private JTextField txtBudget;
    private JTextField txtSavingsGoal;
    private JTextField txtExpenseCategory;
    private JTextField txtExpenseAmount;
    private JButton btnAddExpense;
    private JButton btnUpdateIncome;
    private JButton btnUpdateBudget;
    private JButton btnUpdateSavingsGoal;
    private JTextArea txtExpenseList;
    private JTextArea txtResult;

    private PersonalFinanceManager financeManager;

    public FinanceManagerGUI() {
        setTitle("Personal Finance Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the main panel and set its layout to GridBagLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Initialize the JTextFields
        txtIncome = new JTextField();
        txtBudget = new JTextField();
        txtSavingsGoal = new JTextField();
        txtExpenseCategory = new JTextField();
        txtExpenseAmount = new JTextField();

        // Initialize the JTextAreas
        txtExpenseList = new JTextArea(10, 20);
        txtResult = new JTextArea(5, 20);
        txtResult.setEditable(false);

        // Create panels for grouping components
        JPanel incomeBudgetPanel = new JPanel(new GridLayout(2, 2));
        incomeBudgetPanel.add(new JLabel("Enter your income:"));
        incomeBudgetPanel.add(txtIncome);
        incomeBudgetPanel.add(new JLabel("Enter your budget:"));
        incomeBudgetPanel.add(txtBudget);

        JPanel savingsGoalPanel = new JPanel(new GridLayout(1, 2));
        savingsGoalPanel.add(new JLabel("Enter your savings goal:"));
        savingsGoalPanel.add(txtSavingsGoal);

        JPanel expenseInputPanel = new JPanel(new GridLayout(2, 2));
        expenseInputPanel.add(new JLabel("Enter expense category:"));
        expenseInputPanel.add(txtExpenseCategory);
        expenseInputPanel.add(new JLabel("Enter expense amount:"));
        expenseInputPanel.add(txtExpenseAmount);

        JScrollPane expenseListScrollPane = new JScrollPane(txtExpenseList);
        JScrollPane resultScrollPane = new JScrollPane(txtResult);

        JPanel buttonsPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        btnAddExpense = new JButton("Add Expense");
        btnUpdateIncome = new JButton("Update Income");
        btnUpdateBudget = new JButton("Update Budget");
        btnUpdateSavingsGoal = new JButton("Update Savings Goal");
        buttonsPanel.add(btnAddExpense);
        buttonsPanel.add(btnUpdateIncome);
        buttonsPanel.add(btnUpdateBudget);
        buttonsPanel.add(btnUpdateSavingsGoal);

        // Add components to the main panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(incomeBudgetPanel, gbc);

        gbc.gridy = 1;
        mainPanel.add(savingsGoalPanel, gbc);

        gbc.gridy = 2;
        gbc.gridwidth = 1;
        mainPanel.add(expenseInputPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridheight = 2;
        mainPanel.add(expenseListScrollPane, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 3;
        mainPanel.add(buttonsPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        mainPanel.add(new JLabel("Expense List:"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.gridheight = 3;
        mainPanel.add(resultScrollPane, gbc);

        // Set the main panel as the content pane of the JFrame
        setContentPane(mainPanel);

        // Attach action listeners to buttons
        btnAddExpense.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addExpenseToList();
            }
        });

        btnUpdateIncome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateIncome();
            }
        });

        btnUpdateBudget.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBudget();
            }
        });

        btnUpdateSavingsGoal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateSavingsGoal();
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addExpenseToList() {
        String category = txtExpenseCategory.getText();
        double amount = Double.parseDouble(txtExpenseAmount.getText());

        if (financeManager == null) {
            double income = Double.parseDouble(txtIncome.getText());
            double budget = Double.parseDouble(txtBudget.getText());
            double savingsGoal = Double.parseDouble(txtSavingsGoal.getText());
            financeManager = new PersonalFinanceManager(income, budget, savingsGoal);
        }

        financeManager.addExpense(category, amount);

        txtExpenseList.append(category + ": Rs" + amount + "\n");
        txtExpenseCategory.setText("");
        txtExpenseAmount.setText("");

        updateResults();
    }

    private void updateIncome() {
        double newIncome = Double.parseDouble(txtIncome.getText());
        double totalExpenses = financeManager.calculateTotalExpenses();
        financeManager = new PersonalFinanceManager(newIncome - totalExpenses, financeManager.getBudget(), financeManager.getSavingsGoal());
        updateResults();
    }

    private void updateBudget() {
        double newBudget = Double.parseDouble(txtBudget.getText());
        double totalExpenses = financeManager.calculateTotalExpenses();
        financeManager = new PersonalFinanceManager(financeManager.getIncome() - totalExpenses, newBudget, financeManager.getSavingsGoal());
        updateResults();
    }

    private void updateSavingsGoal() {
        double newSavingsGoal = Double.parseDouble(txtSavingsGoal.getText());
        financeManager = new PersonalFinanceManager(financeManager.getIncome(), financeManager.getBudget(), newSavingsGoal);
        updateResults();
    }

    private void updateResults() {
        if (financeManager != null) {
            double remainingBudget = financeManager.calculateRemainingBudget();
            double savingsProgress = financeManager.calculateSavingsProgress();
            boolean isGoalReached = financeManager.isSavingsGoalReached();

            DecimalFormat df = new DecimalFormat("#.##");

            String resultText = "Remaining budget: Rs" + df.format(remainingBudget) + "\n" +
                    "Savings progress: Rs" + df.format(savingsProgress) + "\n" +
                    "Savings goal reached: " + (isGoalReached ? "Yes" : "No");
            txtResult.setText(resultText);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FinanceManagerGUI();
            }
        });
    }
}

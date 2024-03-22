import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class InteractiveBinaryAddition extends JFrame {
    private JTextField input1, input2;
    private JLabel resultLabel, carryLabel, decimalResultLabel;
    private JPanel stepsPanel;
    private final List<StepDetail> steps;

    private static class StepDetail {
        String description;

        public StepDetail(String description) {
            this.description = description;
        }
    }

    public InteractiveBinaryAddition() {
        steps = new ArrayList<>();
        createUI();
    }

    private void createUI() {
        setTitle("Interactive Binary Addition");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        input1 = new JTextField(8);
        input2 = new JTextField(8);
        JButton addButton = new JButton("Compute");
        resultLabel = new JLabel("Result: ");
        carryLabel = new JLabel("Carry: ");
        decimalResultLabel = new JLabel("Decimal Result: ");
        stepsPanel = new JPanel();
        stepsPanel.setLayout(new BoxLayout(stepsPanel, BoxLayout.Y_AXIS));

        addButton.addActionListener(e -> computeBinaryAddition());

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Input 1:"));
        inputPanel.add(input1);
        inputPanel.add(new JLabel("Input 2:"));
        inputPanel.add(input2);
        inputPanel.add(addButton);

        add(inputPanel);
        add(new JScrollPane(stepsPanel));
        add(resultLabel);
        add(carryLabel);
        add(decimalResultLabel);

        setSize(500, 400);
        setVisible(true);
    }

    private void computeBinaryAddition() {
        StringBuilder bin1 = new StringBuilder(input1.getText());
        StringBuilder bin2 = new StringBuilder(input2.getText());
        steps.clear();
        stepsPanel.removeAll();

        while (bin1.length() < bin2.length()) bin1.insert(0, "0");
        while (bin2.length() < bin1.length()) bin2.insert(0, "0");

        int carry = 0;
        StringBuilder result = new StringBuilder();

        for (int i = bin1.length() - 1; i >= 0; i--) {
            int bit1 = bin1.charAt(i) - '0';
            int bit2 = bin2.charAt(i) - '0';
            int sum = bit1 + bit2 + carry;

            int newCarry = sum / 2;
            sum = sum % 2;

            steps.add(new StepDetail(String.format("Adding %d + %d + Carry: %d = %d", bit1, bit2, carry, sum)));
            carry = newCarry;
            result.insert(0, sum);
        }

        if (carry > 0) {
            result.insert(0, carry);
        }

        String binaryResult = result.toString();
        resultLabel.setText("Result: " + binaryResult);
        carryLabel.setText("Final Carry: " + carry);
        displaySteps();
        displayDecimalConversion(binaryResult);
    }

    private void displaySteps() {
        for (StepDetail step : steps) {
            stepsPanel.add(new JLabel(step.description));
        }
        stepsPanel.revalidate();
        stepsPanel.repaint();
    }

    private void displayDecimalConversion(String binaryResult) {
        long decimalValue = 0;
        StringBuilder conversionSteps = new StringBuilder("<html>Conversion to Decimal:<br>");
        for (int i = 0; i < binaryResult.length(); i++) {
            int bitValue = binaryResult.charAt(i) - '0';
            double pow = Math.pow(2, binaryResult.length() - i - 1);
            decimalValue += (long) (bitValue * pow);
            conversionSteps.append(String.format("%d * 2^%d = %d<br>", bitValue, binaryResult.length() - i - 1, (int)(bitValue * pow)));
        }
        conversionSteps.append(String.format("Decimal Result: %d</html>", decimalValue));
        decimalResultLabel.setText(conversionSteps.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InteractiveBinaryAddition::new);
    }
}

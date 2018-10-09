package ru.ifmo.ctddev.fr0streaper.calculator;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import expression.exceptions.ExpressionParser;
import expression.operations.DoubleOperator;

public class MainActivity extends AppCompatActivity {

    private static String EXPR = MainActivity.class.getName() + ".expr";

    ExpressionParser<Double> parser;
    Button plus, minus, multiply, divide, open_parenthesis, close_parenthesis, equals, dot, del;
    Button[] numbers = new Button[10];
    TextView expression, result;

    private void reevaluate() {
        try {
            String currentExpression = expression.getText().toString();
            String currentResult = parser.parse(currentExpression).evaluate(0d, 0d, 0d).toString();
            result.setText(currentResult);
        }
        catch (Exception e)
        {
            result.setText("");
        }
    }

    private void onClickListenerCreator(Button button, final String action) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expression.setText(expression.getText() + action);
                reevaluate();
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parser = new ExpressionParser<>(new DoubleOperator());

        plus = findViewById(R.id.button_plus);
        minus = findViewById(R.id.button_minus);
        multiply = findViewById(R.id.button_multiply);
        divide = findViewById(R.id.button_divide);
        open_parenthesis = findViewById(R.id.button_open_parenthesis);
        close_parenthesis = findViewById(R.id.button_close_parenthesis);
        equals = findViewById(R.id.button_equals);
        dot = findViewById(R.id.button_dot);
        del = findViewById(R.id.button_del);

        expression = findViewById(R.id.textView_expression);
        result = findViewById(R.id.textView_result);

        onClickListenerCreator(plus, "+");
        onClickListenerCreator(minus, "-");
        onClickListenerCreator(multiply, "*");
        onClickListenerCreator(divide, "/");
        onClickListenerCreator(open_parenthesis, "(");
        onClickListenerCreator(close_parenthesis, ")");
        onClickListenerCreator(dot, ".");

        numbers[0] = findViewById(R.id.button0);
        numbers[1] = findViewById(R.id.button1);
        numbers[2] = findViewById(R.id.button2);
        numbers[3] = findViewById(R.id.button3);
        numbers[4] = findViewById(R.id.button4);
        numbers[5] = findViewById(R.id.button5);
        numbers[6] = findViewById(R.id.button6);
        numbers[7] = findViewById(R.id.button7);
        numbers[8] = findViewById(R.id.button8);
        numbers[9] = findViewById(R.id.button9);

        onClickListenerCreator(numbers[0], "0");
        onClickListenerCreator(numbers[1], "1");
        onClickListenerCreator(numbers[2], "2");
        onClickListenerCreator(numbers[3], "3");
        onClickListenerCreator(numbers[4], "4");
        onClickListenerCreator(numbers[5], "5");
        onClickListenerCreator(numbers[6], "6");
        onClickListenerCreator(numbers[7], "7");
        onClickListenerCreator(numbers[8], "8");
        onClickListenerCreator(numbers[9], "9");

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = expression.getText().length();
                if (size > 0) {
                    if (size == 1) {
                        expression.setText("");
                    }
                    else {
                        expression.setText(expression.getText().subSequence(0, size - 2));
                    }
                }
                reevaluate();
            }
        });

        equals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String currentExpression = expression.getText().toString();
                    String currentResult = parser.parse(currentExpression).evaluate(0d, 0d, 0d).toString();
                    expression.setText(currentResult);
                    result.setText("");
                }
                catch (Exception e)
                {
                    result.setText(e.getMessage());
                }
            }
        });

        if (savedInstanceState != null) {
            expression.setText(savedInstanceState.getCharSequence(EXPR));
            reevaluate();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putCharSequence(EXPR, expression.getText());

        super.onSaveInstanceState(outState);
    }
}

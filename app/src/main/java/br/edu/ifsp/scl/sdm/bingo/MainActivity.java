package br.edu.ifsp.scl.sdm.bingo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private static final int MAX_NUMBER = 75;
    private static final String EXTRA_DRAWN_NUMBERS = "MainActivity.DrawnNumbers";

    private ArrayList<Integer> mPossibilities;
    private ArrayList<Integer> mDrawnNumbers;

    private GridLayout mDrawnNumbersGridLayout;
    private Button mDrawButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null && savedInstanceState.containsKey(EXTRA_DRAWN_NUMBERS)) {
            mDrawnNumbers = savedInstanceState.getIntegerArrayList(EXTRA_DRAWN_NUMBERS);
        } else {
            mDrawnNumbers = new ArrayList<>();
        }
        this.bindViews();
        this.drawNumbers();
        this.generatePossibilities();
    }

    private void bindViews() {
        mDrawnNumbersGridLayout = findViewById(R.id.glDrawnNumbers);
        mDrawButton = findViewById(R.id.btDraw);

        mDrawnNumbersGridLayout.setColumnCount(getResources().getDisplayMetrics().widthPixels / getResources().getDimensionPixelSize(R.dimen.number_width));
        mDrawButton.setOnClickListener(v -> this.drawNumber());

    }

    private void drawNumbers() {
        for (final Integer number : mDrawnNumbers) {
            this.drawNumber(number);
        }
    }

    private void generatePossibilities() {
        mPossibilities = new ArrayList<>();
        for (int number = 1; number <= MAX_NUMBER; number++) {
            if (!mDrawnNumbers.contains(number)) {
                mPossibilities.add(number);
            }
        }
        this.checkPossibilities();
    }

    private void drawNumber() {
        Collections.shuffle(mPossibilities);
        final Integer number = mPossibilities.remove(0);
        mDrawnNumbers.add(number);
        this.drawNumber(number);
        this.checkPossibilities();
    }

    private void checkPossibilities() {
        if (mPossibilities.isEmpty()) {
            mDrawButton.setEnabled(false);
        }
    }

    private void drawNumber(@NonNull Integer number) {
        final TextView textView = new TextView(this);
        textView.setText(String.valueOf(number));
        textView.setTextAppearance(this, R.style.TextAppearance_AppCompat_Large);
        textView.setGravity(Gravity.CENTER);
        final GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
        layoutParams.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        layoutParams.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        textView.setLayoutParams(layoutParams);
        mDrawnNumbersGridLayout.addView(textView);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putIntegerArrayList(EXTRA_DRAWN_NUMBERS, mDrawnNumbers);
    }
}

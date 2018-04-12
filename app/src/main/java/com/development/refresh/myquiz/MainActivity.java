package com.development.refresh.myquiz;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import lombok.NonNull;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    // IDs of each of the correct choices based on template defined in cardview.xml
    private final static List<Integer> answers = Arrays.asList(R.id.c, R.id.a, R.id.c, R.id.d, R.id.b, R.id.b);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((TextView)findViewById(R.id.title)).setTypeface(FontMachine.getFont(this, "Android-Insomnia-Regular.ttf"));

        recyclerView = findViewById(R.id.recycler_view);
        final LinearLayoutManager lLayoutManager= new LinearLayoutManager(this);
        lLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        final List<Question> questions = initializeData();

        final Resources res = getResources();
        QuestionAdapter.QuestionViewHolder.modify(this);
        final RecyclerView.Adapter mAdapter = new QuestionAdapter(questions, Arrays.asList(
                res.getString(R.string.q1_text),
                res.getString(R.string.q2_text),
                res.getString(R.string.q3_text),
                res.getString(R.string.q4_text),
                res.getString(R.string.q5_text),
                res.getString(R.string.q6_text)));

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(lLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    private @NonNull List<Question> initializeData() {
        return Arrays.asList(
            new Question(R.string.q1_text, Arrays.asList(R.string.q1a, R.string.q1b, R.string.q1c, R.string.q1d)),
            new Question(R.string.q2_text, Arrays.asList(R.string.q2a, R.string.q2b, R.string.q2c, R.string.q2d)),
            new Question(R.string.q3_text, Arrays.asList(R.string.q3a, R.string.q3b, R.string.q3c, R.string.q3d)),
            new Question(R.string.q4_text, Arrays.asList(R.string.q4a, R.string.q4b, R.string.q4c, R.string.q4d)),
            new Question(R.string.q5_text, Arrays.asList(R.string.q5a, R.string.q5b, R.string.q5c, R.string.q5d)),
            new Question(R.string.q6_text, Arrays.asList(R.string.q6a, R.string.q6b, R.string.q6c, R.string.q6d)));
    }

    public void checkEm(final View v) {
        int correct = 0;
        List<Integer> selected = ((QuestionAdapter) recyclerView.getAdapter()).getSelected();
        for (int i = 0; i < answers.size(); i++) {
            if (answers.get(i).equals(selected.get(i)))
                correct++;
            else
                Toast.makeText(this, String.format(getResources().getString(R.string.incorrect), i+1), Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this, String.format(getResources().getString(R.string.report), correct, answers.size()), Toast.LENGTH_LONG).show();
    }
}

class FontMachine {
    static Typeface getFont(Context context, String path) {
        return Typeface.createFromAsset(context.getAssets(), path);
    }
}
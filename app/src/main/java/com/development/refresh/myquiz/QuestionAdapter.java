package com.development.refresh.myquiz;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
final class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {
    @Getter
    private @NonNull List<Question> questions;
    @Getter
    // user-selected answers are indexed based on question order
    private final List<Integer> selected = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0));
    // in order to infer question order, use the string ID of each question as an index
    private @NonNull final List<String> mappings;

    static class QuestionViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView question;
        List<RadioButton> choices;
        RadioGroup radioGroup;

        // note I tried finding other ways to change fonts within a static context but it was very difficult
        static Context context = null;

        QuestionViewHolder(final View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.card);
            question = itemView.findViewById(R.id.q_text);
            // use custom font
            question.setTypeface(FontMachine.getFont(context, "Green-Avocado.ttf"));
            choices = Arrays.asList(
                    (RadioButton)itemView.findViewById(R.id.a),
                    (RadioButton)itemView.findViewById(R.id.b),
                    (RadioButton)itemView.findViewById(R.id.c),
                    (RadioButton)itemView.findViewById(R.id.d));

            radioGroup = itemView.findViewById(R.id.group);
            radioGroup.setTag(question);
        }

        public static void modify(Context context) { QuestionViewHolder.context = context; }
    }

    @Override
    public void onBindViewHolder(QuestionViewHolder questionHolder, int position) {
        questionHolder.question.setText(questions.get(position).getQuestionStrId());

        for (int i = 0; i < questionHolder.choices.size(); i++) {
            questionHolder.choices.get(i).setText(questions.get(position).getChoice_str_ids().get(i));

        }

        questionHolder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.d("RADIO GROUP: ", String.format("CHECKED_ID = %d", checkedId));
                selected.add(mappings.indexOf(((TextView)group.getTag()).getText().toString()), checkedId);
            }
        });
    }

    @Override
    public void onBindViewHolder(QuestionViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        return new QuestionViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }
}


@Getter
@AllArgsConstructor
final class Question {
    private final int questionStrId;
    private final List<Integer> choice_str_ids;
}
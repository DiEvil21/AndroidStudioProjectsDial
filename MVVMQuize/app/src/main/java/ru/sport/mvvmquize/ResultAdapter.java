package ru.sport.mvvmquize;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultViewHolder> {
    private List<Question> questions;
    private List<Integer> userAnswers;

    public ResultAdapter(List<Question> questions, List<Integer> userAnswers) {
        this.questions = questions;
        this.userAnswers = userAnswers;
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_result, parent, false);
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {
        Question question = questions.get(position);
        int userAnswer = userAnswers.get(position);
        int correctOptionIndex = question.getCorrectOptionIndex();

        holder.questionTextView.setText(question.getQuestionText());

        List<String> options = question.getOptions();

        RadioGroup optionsRadioGroup = holder.itemView.findViewById(R.id.optionsRadioGroup);
        optionsRadioGroup.removeAllViews();

        for (int i = 0; i < options.size(); i++) {
            String option = options.get(i);

            RadioButton radioButton = new RadioButton(holder.itemView.getContext());
            radioButton.setText(option);

            if (i == userAnswer && i != correctOptionIndex) {
                radioButton.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.red)); // неверный ответ - красный цвет
            }

            if (i == correctOptionIndex) {
                radioButton.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.green)); // верный ответ - зеленый цвет
            }

            optionsRadioGroup.addView(radioButton);
        }
    }



    @Override
    public int getItemCount() {
        return questions.size();
    }

    static class ResultViewHolder extends RecyclerView.ViewHolder {
        TextView questionTextView;
        RadioGroup optionsRadioGroup;

        ResultViewHolder(View itemView) {
            super(itemView);
            questionTextView = itemView.findViewById(R.id.questionTextView);
            optionsRadioGroup = itemView.findViewById(R.id.optionsRadioGroup);
        }
    }
}


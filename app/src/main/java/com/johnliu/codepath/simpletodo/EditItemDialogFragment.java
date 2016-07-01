package com.johnliu.codepath.simpletodo;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by johnliu on 6/29/16.
 */
public class EditItemDialogFragment extends DialogFragment implements TextView.OnEditorActionListener {

    // Defines the listener interface
    public interface EditItemDialogListener {
        void onFinishEditDialog(ToDoItem inputText);
    }


    private EditText mEditText;
    private TextView mDueDate;

    private ToDoItem toDoItem;

    public EditItemDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static EditItemDialogFragment newInstance(String title) {
        EditItemDialogFragment frag = new EditItemDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_edit_item, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        mEditText = (EditText) view.findViewById(R.id.editText);
        mDueDate = (TextView) view.findViewById(R.id.duedate);
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Edit Todo Item");
        getDialog().setTitle(title);
        String item = getArguments().getString("item", "");
        mEditText.setText(item);
        String dueDate =  getArguments().getString("dueDate", "");
        mDueDate.setText(dueDate);

        // Show soft keyboard automatically and request focus to field
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (EditorInfo.IME_ACTION_DONE == i) {
            // Return input text back to activity through the implemented listener
            EditItemDialogListener listener = (EditItemDialogListener) getActivity();
            toDoItem.item = mEditText.getText().toString();
            listener.onFinishEditDialog(toDoItem);
            // Close the dialog and return back to the parent activity
            dismiss();
            return true;
        }
        return false;
    }

}

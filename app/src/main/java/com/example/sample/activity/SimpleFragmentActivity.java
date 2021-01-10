package com.example.sample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sample.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class SimpleFragmentActivity extends FragmentActivity implements BookListFragment.Callback {

    private boolean twoPane = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_fragment);
        if (findViewById(R.id.book_detail) == null) {
            twoPane = false;
            ((BookListFragment)getSupportFragmentManager().findFragmentById(R.id.book_list)).setActivateOnItemClick(true);
        }
    }

    @Override
    public void onListItemSelect(int position) {
        Bundle data = new Bundle();
        data.putInt("position", position);
        if (twoPane) {
            BookDetailFragment bookDetailFragment = new BookDetailFragment();
            bookDetailFragment.setArguments(data);
            getSupportFragmentManager().beginTransaction().replace(R.id.book_detail, bookDetailFragment).commit();
        } else {
            Intent intent = new Intent(this, BookDetailActivity.class);
            intent.putExtras(data);
            startActivity(intent);
        }
    }

    public static class BookDetailFragment extends Fragment {

        private BookManager.Book book;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Bundle data = getArguments();
            if (data != null) {
                int position = data.getInt("position");
                book = BookManager.getBook(position);
            }
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View inflate = inflater.inflate(R.layout.book_detail, container, false);
            if (book == null) {
                return inflate;
            }
            TextView title = inflate.findViewById(R.id.title);
            title.setText(book.getTitle());
            TextView detail = inflate.findViewById(R.id.detail);
            detail.setText(book.getDetail());
            return inflate;
        }
    }

}
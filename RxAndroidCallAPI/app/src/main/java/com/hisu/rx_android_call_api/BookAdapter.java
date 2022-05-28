package com.hisu.rx_android_call_api;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hisu.rx_android_call_api.databinding.LayoutBookBinding;

import java.util.List;
import java.util.Locale;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<Book> books;

    public BookAdapter(List<Book> books) {
        this.books = books;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutBookBinding binding = LayoutBookBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );

        return new BookViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        holder.bindBookData(books.get(position));
    }

    @Override
    public int getItemCount() {
        return books == null ? 0 : books.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {

        private final LayoutBookBinding binding;

        public BookViewHolder(@NonNull LayoutBookBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bindBookData(Book book) {
            binding.tvBookInfo.setText(
                    String.format(Locale.US,
                            "%d. %s; %s; %s",
                            book.getId(), book.getName(), book.getType(), book.isAvailable()
                    ));
        }
    }
}
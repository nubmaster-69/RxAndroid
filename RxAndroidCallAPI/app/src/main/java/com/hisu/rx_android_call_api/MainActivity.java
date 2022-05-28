package com.hisu.rx_android_call_api;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.hisu.rx_android_call_api.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Disposable mDisposable;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(this);
        binding.bookRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        binding.btnGetAll.setOnClickListener(view -> getAllBooksFromApi());
        binding.btnTypeNonFiction.setOnClickListener(view -> getBooksFromApiByType("non-fiction"));
        binding.btnTypeFiction.setOnClickListener(view -> getBooksFromApiByType("fiction"));
    }

    private void getAllBooksFromApi() {
        progressDialog.setMessage("We're working on it! Please wait...");
        progressDialog.show();

        ApiService.apiService.getBooks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getBookObserver());
    }

    private void getBooksFromApiByType(String type) {
        progressDialog.setMessage("We're working on it! Please wait...");
        progressDialog.show();

        ApiService.apiService.getBooksByType(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getBookObserver());
    }

    private Observer<List<Book>> getBookObserver() {
        return new Observer<List<Book>>() {

            private List<Book> books;

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mDisposable = d;
                books = new ArrayList<>();
                LogUtils.log("Subscribe", "Subscribed Successfully!");
            }

            @Override
            public void onNext(@NonNull List<Book> books) {
                this.books.addAll(books);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                LogUtils.log("Response Error", e.getMessage());
            }

            @Override
            public void onComplete() {
                progressDialog.dismiss();
                BookAdapter bookAdapter = new BookAdapter(books);
                binding.bookRecyclerView.setAdapter(bookAdapter);
            }
        };
    }

    @Override
    protected void onDestroy() {
        if (mDisposable != null)
            mDisposable.dispose();
        super.onDestroy();
    }
}
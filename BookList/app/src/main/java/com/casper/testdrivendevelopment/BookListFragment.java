package com.casper.testdrivendevelopment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookListFragment extends Fragment {

    BookListMainActivity.BooksArrayAdapter booksArrayAdapter;
    public BookListFragment(BookListMainActivity.BooksArrayAdapter booksArrayAdapter) {
        // Required empty public constructor
        this.booksArrayAdapter=booksArrayAdapter;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);
        return textView;*/
        View view=inflater.inflate(R.layout.fragment_book_list, container, false);
        ListView listViewSuper= (ListView) view.findViewById(R.id.list_view_books);
        listViewSuper.setAdapter(booksArrayAdapter);

        this.registerForContextMenu(listViewSuper);
        // Inflate the layout for this fragment
        return view;
    }

}

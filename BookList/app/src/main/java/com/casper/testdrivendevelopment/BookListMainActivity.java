package com.casper.testdrivendevelopment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class BookListMainActivity extends AppCompatActivity {

    private ArrayList<Book> theBooks;
    private ListView listViewSuper;
    private BooksArrayAdapter theAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list_main);

        InitData();

        listViewSuper= (ListView) this.findViewById(R.id.list_view_books);
        theAdapter=new BooksArrayAdapter(this,R.layout.list_item,theBooks);
        listViewSuper.setAdapter(theAdapter);
        this.registerForContextMenu(listViewSuper);
    }

    //添加长按点击弹出选择菜单
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if(v==listViewSuper) {
            int itemPosition=((AdapterView.AdapterContextMenuInfo)menuInfo).position;
            menu.setHeaderTitle(theBooks.get(itemPosition).getTitle());
            menu.add(0, 1, 0, "新建");
            menu.add(0, 2, 0, "删除");
            menu.add(0, 3, 0, "关于...");
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case 1: {
                AdapterView.AdapterContextMenuInfo menuInfo=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();//获取上下文；
                theBooks.add(menuInfo.position, new Book("new book",R.drawable.a4));
                theAdapter.notifyDataSetChanged();

                Toast.makeText(this, "新建成功", Toast.LENGTH_SHORT).show();

                break;
            }
            case 2: {
                AdapterView.AdapterContextMenuInfo menuInfo=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                final int itemPosition=menuInfo.position;
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_lock_idle_alarm)
                        .setTitle("询问")
                        .setMessage("你确定要删除这条吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                theBooks.remove(itemPosition);
                                theAdapter.notifyDataSetChanged();
                                Toast.makeText(BookListMainActivity.this, "删除成功！", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .create().show();
                break;
            }
            case 3:
                Toast.makeText(this, "版权所有by shpchen!", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onContextItemSelected(item);
    }


    private void InitData() {
        theBooks=new ArrayList<Book>();
        theBooks.add(new Book("软件项目管理案例教程（第4版）", R.drawable.book_2));
        theBooks.add(new Book("创新工程实践",R.drawable.book_no_name));
        theBooks.add(new Book("信息安全数学基础（第2版）",R.drawable.book_1));
    }


    public List<Book> getListBooks() {
        return theBooks;
    }

    protected class BooksArrayAdapter extends ArrayAdapter<Book>
    {
        private  int resourceId;
        public BooksArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Book> objects) {
            super(context, resource, objects);
            resourceId=resource;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater mInflater= LayoutInflater.from(this.getContext());
            View item = mInflater.inflate(this.resourceId,null);

            ImageView img = (ImageView)item.findViewById(R.id.image_view_book_cover);
            TextView name = (TextView)item.findViewById(R.id.text_view_book_title);

            Book good_item= this.getItem(position);
            img.setImageResource(good_item.getCoverResourceId());
            name.setText(good_item.getTitle());

            return item;
        }
    }
}

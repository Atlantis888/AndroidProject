package com.casper.testdrivendevelopment;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import com.casper.testdrivendevelopment.data.BookFragmentAdapter;
import com.casper.testdrivendevelopment.BookListFragment;
import com.casper.testdrivendevelopment.data.FileDataSource;
import com.casper.testdrivendevelopment.data.model.Book;

import java.util.ArrayList;
import java.util.List;


public class BookListMainActivity extends AppCompatActivity {

    public static final int CONTEXT_MENU_ITEM_NEW = 1;
    public static final int CONTEXT_MENU_ITEM_UPDATE= CONTEXT_MENU_ITEM_NEW+1;
    public static final int CONTEXT_MENU_ITEM_DELETE = CONTEXT_MENU_ITEM_UPDATE+1;
    public static final int CONTEXT_MENU_ITEM_ABOUT = CONTEXT_MENU_ITEM_DELETE+1;
    public static final int REQUEST_CODE_NEW_GOOD = 901;
    public static final int REQUEST_CODE_UPDATE_GOOD = 902;
    private FileDataSource fileDataSource;
    private ArrayList<Book> theBooks;
    //private ListView listViewSuper;
    private BooksArrayAdapter theAdapter;
    @Override
    protected void onDestroy() {
        super.onDestroy();
        fileDataSource.save();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list_main);
        /*
        fileDataSource=new FileDataSource(this);
        theBooks=fileDataSource.load();
        if(theBooks.size()==0)
            InitData();
        listViewSuper= (ListView) this.findViewById(R.id.list_view_books);
        theAdapter=new BooksArrayAdapter(this,R.layout.list_item,theBooks);
        listViewSuper.setAdapter(theAdapter);
        this. registerForContextMenu(listViewSuper);
    */
        InitData();

        theAdapter=new BooksArrayAdapter(this,R.layout.list_item,theBooks);

        BookFragmentAdapter myPageAdapter = new BookFragmentAdapter(getSupportFragmentManager());

        ArrayList<Fragment> datas = new ArrayList<Fragment>();
        datas.add(new BookListFragment(theAdapter));
        datas.add(new WebViewFragment());
        datas.add(new WebViewFragment());
        myPageAdapter.setData(datas);

        ArrayList<String> titles = new ArrayList<String>();
        titles.add("图书");
        titles.add("新闻");
        titles.add("卖家");
        myPageAdapter.setTitles(titles);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(myPageAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }


    //添加长按点击弹出选择菜单
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if(v==this.findViewById(R.id.list_view_books)) {
            int itemPosition=((AdapterView.AdapterContextMenuInfo)menuInfo).position;
            menu.setHeaderTitle(theBooks.get(itemPosition).getTitle());
            menu.add(0, CONTEXT_MENU_ITEM_NEW, 0, "新建");
            menu.add(0, CONTEXT_MENU_ITEM_UPDATE, 0, "修改");
            menu.add(0, CONTEXT_MENU_ITEM_DELETE, 0, "删除");
            menu.add(0, CONTEXT_MENU_ITEM_ABOUT, 0, "关于...");
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode)
        {
            case REQUEST_CODE_NEW_GOOD:
                if(resultCode==RESULT_OK)
                {
                    int position=data.getIntExtra("edit_position",0);
                    String name=data.getStringExtra("good_name");
                    theBooks.add(position, new Book(name,R.drawable.a4));
                    theAdapter.notifyDataSetChanged();

                    Toast.makeText(this, "新建成功", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_UPDATE_GOOD:
                if(resultCode==RESULT_OK)
                {
                    int position=data.getIntExtra("edit_position",0);
                    String name=data.getStringExtra("good_name");

                    Book good=theBooks.get(position);
                    good.setName(name);
                    theAdapter.notifyDataSetChanged();

                    Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case CONTEXT_MENU_ITEM_NEW: {
                AdapterView.AdapterContextMenuInfo menuInfo=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();//获取上下文；
                Intent intent = new Intent(BookListMainActivity.this,EditTextActivity.class);
                intent.putExtra("edit_position",menuInfo.position);
                startActivityForResult(intent, REQUEST_CODE_NEW_GOOD);
                break;
            }
            case CONTEXT_MENU_ITEM_UPDATE: {
                AdapterView.AdapterContextMenuInfo menuInfo=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

                Book book=theBooks.get(menuInfo.position);

                Intent intent = new Intent(BookListMainActivity.this,EditTextActivity.class);
                intent.putExtra("edit_position",menuInfo.position);
                intent.putExtra("good_name",book.getTitle());
                startActivityForResult(intent, REQUEST_CODE_UPDATE_GOOD);
                break;
            }
            case CONTEXT_MENU_ITEM_DELETE: {
                AdapterView.AdapterContextMenuInfo menuInfo=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                final int itemPosition=menuInfo.position;
                new android.app.AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
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
            case CONTEXT_MENU_ITEM_ABOUT:
                Toast.makeText(this, "版权所有by shpchen!", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onContextItemSelected(item);
    }



    private void InitData() {
        fileDataSource=new FileDataSource(this);
        theBooks=fileDataSource.load();
        if(theBooks.size()==0) {
            theBooks.add(new Book("目前没有书", R.drawable.a4));
        }
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

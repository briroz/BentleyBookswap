package com.briroz.bentleybookswap;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookListActivityByClass extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner classCategory;
    private ListView bookList;
    private boolean success = false; // boolean
    private ConnectionClass connectionClass; //Connection Class Variable
    private ArrayList<ListItemsClass> bookArraryList;  //List items Array
    private BookListAdapter myBookListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list_by_class);
        classCategory = findViewById(R.id.spinnerClassCategory);
        classCategory.setOnItemSelectedListener(this);
        bookList = findViewById(R.id.classSortedBookList);

        connectionClass = new ConnectionClass(); // Connection Class Initialization
        bookArraryList = new ArrayList<ListItemsClass>(); // Arraylist Initialization

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // Calling Async Task
        SyncData orderData = new SyncData();
        orderData.execute("");
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // Do nothing until the user selects the class category
    }

    // Async Task Overrides 3 methods and displays a dialog while executing the SQL query.  Animation should be added to the dialog
    private class SyncData extends AsyncTask<String, String, String>
    {
        ProgressDialog progress;

        @Override
        protected void onPreExecute() //Starts the progress dailog
        {
            progress = ProgressDialog.show(BookListActivityByClass.this, "Synchronising",
                    "Book List Loading! Please Wait...", true);
        }

        @Override
        protected String doInBackground(String...strings )  {        // Connect to the db, write query and add items to arraylist
            String msg = "No error";
            try {
                Connection conn = connectionClass.CONN(); //Connection Object
                if (conn == null) {
                    success = false;
                } else {
                    // Change below query according to your own database.
                    String query = "SELECT itemKey, bookTitle, bookAuthor, isbn FROM saleItems;";
                    // Prepared statement goes HERE
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs != null) {    // if result set is not null, add items to itemArraylist using custom class
                        while (rs.next()) {
                            try {
                                bookArraryList.add(new ListItemsClass(rs.getInt("itemKey"), rs.getString("bookTitle"), rs.getString("bookAuthor"), rs.getString("isbn")));  // Adds item of type ListItemsClass(int, String, String, String) to array list
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                        Log.d("TAG", "DATA FOUND");
                        success = true;
                    } else {
                       Log.d("TAG", "No Data found!");
                        success = false;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                /* Writer writer = new StringWriter();  // consider removal
                e.printStackTrace(new PrintWriter(writer));
                msg = writer.toString();  */
                success = false;
            }
            return msg;
        }

        @Override
        protected void onPostExecute(String msg) {           // dismissing progress dialog, showing error and setting up my listview
            progress.dismiss();
            Toast.makeText(BookListActivityByClass.this, msg + "", Toast.LENGTH_LONG).show();
            if (success == false) {

            } else {
                try {
                    myBookListAdapter = new BookListAdapter(bookArraryList, BookListActivityByClass.this);
                    bookList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    bookList.setAdapter(myBookListAdapter);
                } catch (Exception ex) {

                }
            }
        }
    }

    public class BookListAdapter extends BaseAdapter {         //has a class viewholder which holds
        public class ViewHolder {
            TextView ListViewTitle;
            TextView ListViewAuthor;
            ImageView imageView;
        }

        public List<ListItemsClass> bookItemsList;

        public Context context;
        ArrayList<ListItemsClass> arraylist;

        private BookListAdapter(List<ListItemsClass> books, Context context) {
            this.bookItemsList = books;
            this.context = context;
            arraylist = new ArrayList<ListItemsClass>();
            arraylist.addAll(bookItemsList);
        }

        @Override
        public int getCount() {
            return bookItemsList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {       // inflating the layout and initializing widgets
            View rowView = convertView;
            ViewHolder viewHolder= null;
            if (rowView == null) {
                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.list_content, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.ListViewTitle = (TextView) rowView.findViewById(R.id.ListItemTitle);
                viewHolder.ListViewAuthor = (TextView) rowView.findViewById(R.id.ListItemAuthor);
                viewHolder.imageView = (ImageView) rowView.findViewById(R.id.imageView);
                rowView.setTag(viewHolder);
            }
            else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            // here setting up names and images
            viewHolder.ListViewTitle.setText(bookItemsList.get(position).getTitle()+"");
            viewHolder.ListViewAuthor.setText(bookItemsList.get(position).getAuthor()+"");
            Picasso.get().load("http://covers.openlibrary.org/b/isbn/"+ bookItemsList.get(position).getIsbn()+"-S.jpg").into(viewHolder.imageView);   // Uses OpenLibrary to generate small images using the ISBN to display inside of the image frame
            return rowView;
        }
    }
}

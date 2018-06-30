package tk.zielony.carbonsamples.widget;

import android.app.Activity;
import android.os.Bundle;

import java.text.Format;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import carbon.Carbon;
import carbon.beta.TableLayout;
import carbon.widget.TableView;
import tk.zielony.carbonsamples.R;

/**
 * Created by Marcin on 2015-12-20.
 */
public class TableLayoutActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablelayout);

        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        List<List<Object>> items = new ArrayList<>();
        items.add(Arrays.asList(new Object[]{"Samsung", "GT-9001", 0.32f, false}));
        items.add(Arrays.asList(new Object[]{"Sony", "Z3c", 0.14f, false}));
        items.add(Arrays.asList(new Object[]{"Google", "emulator", 0.10f, true}));
        List<String> names = Arrays.asList("Manufacturer", "Model", "Percentage", "Emulator");
        List<Class> classes = Arrays.asList(new Class[]{String.class, String.class, Float.class, Boolean.class});
        tableLayout.setAdapter(new MyTableAdapter(tableLayout.getTableView(), items, names, classes));
        tableLayout.getToolbar().setTitle("Devices");
        tableLayout.getToolbar().getTitleView().setTextColor(Carbon.getThemeColor(this, android.R.attr.textColorSecondary));
    }

    public static class MyTableAdapter extends TableView.Adapter {

        private final List<List<Object>> items;
        private List<String> names;
        private final List<Class> classes;

        public MyTableAdapter(TableView tableView, List<List<Object>> items, List<String> names, List<Class> classes) {
            super(tableView);
            this.items = items;
            this.names = names;
            this.classes = classes;
        }

        @Override
        public String getColumnName(int column) {
            return names.get(column);
        }

        @Override
        public Class getColumnClass(int column) {
            return classes.get(column);
        }

        @Override
        public int getColumnCount() {
            return names.size();
        }

        @Override
        public List<Object> getItem(int position) {
            return items.get(position);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        @Override
        public Format getColumnFormat(int column) {
            if (column == 2)
                return NumberFormat.getPercentInstance();
            return null;
        }
    }
}

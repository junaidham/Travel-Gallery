package tech.ducletran.travelgallery.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import tech.ducletran.travelgallery.Adapter.CityCountryItemAdapter;
import tech.ducletran.travelgallery.R;

import java.util.ArrayList;

public class CityFragment extends Fragment {

    private static CityCountryItemAdapter adapter;
    private static ListView listView;
    private static ArrayList<String> citiesList;
    private static TextView textView;

    public CityFragment() {super();}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city_country_view,container,false);

        listView = view.findViewById(R.id.city_country_list_view);
        textView = view.findViewById(R.id.city_country_text_view);
        final TextView titleTextView = view.findViewById(R.id.city_country_title_text_view);


        citiesList = new ArrayList<>(MapFragment.getCitiesList());
        textView.setText("You have travelled to " + citiesList.size() + " cities.");
        titleTextView.setText("List of cities");

        adapter = new CityCountryItemAdapter(getActivity(), citiesList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder editDialog = new AlertDialog.Builder(getActivity());
                editDialog.setTitle("Editting this city");
                editDialog.setMessage("Change the name of this city or delete it");

                final String currentName = citiesList.get(position);
                View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_rename_album_layout,null);
                final EditText dialogEditText = dialogView.findViewById(R.id.album_rename_edit_text);

                editDialog.setView(dialogView);
                dialogEditText.setText(currentName);

                editDialog.setNegativeButton("delete",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        citiesList.remove(position);
                        adapter.notifyDataSetChanged();
                        listView.invalidate();
                        MapFragment.deleteCity(currentName);
                        textView.setText("You have travelled to " + citiesList.size() + " cities.");
                        dialog.cancel();
                    }
                });

                editDialog.setNeutralButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                editDialog.setPositiveButton("edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newName = dialogEditText.getText().toString();
                        if (!TextUtils.isEmpty(newName) || newName.equals(currentName)) {
                            citiesList.remove(position);
                            citiesList.add(position,currentName);
                            MapFragment.editCity(currentName,newName);
                            adapter.notifyDataSetChanged();
                            listView.invalidate();
                        } else {
                            Toast.makeText(getActivity(),"Name of this city is not good enough",Toast.LENGTH_SHORT).show();
                        }

                        dialog.cancel();
                    }
                });

                editDialog.show();
            }
        });

        return view;
    }

    public static void addNewCity(String newCity) {
        citiesList.add(newCity);
        textView.setText("You have travelled to " + citiesList.size() + " cities.");
        adapter.notifyDataSetChanged();
        listView.invalidate();
    }

}

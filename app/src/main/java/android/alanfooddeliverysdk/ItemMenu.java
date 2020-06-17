package android.alanfooddeliverysdk;

import android.alanfooddeliverysdk.data.CartItem;
import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;
import java.util.Locale;


public class ItemMenu extends Fragment {

    public ItemMenu() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final MainActivity MA = ((MainActivity) requireActivity());

        View view = inflater.inflate(R.layout.fragment_item_menu, container, false);
        //Toolbar toolbar = MA.findViewById(R.id.id_app_toolbar);
        final List<CartItem> cartItems = MA.items.get(MA.route);
        final OrderItems orderItems =  new OrderItems(view, MA.orderedItemsList);
        LinearLayout mainLayout = view.findViewById(R.id.itemMenuCart);
        LayoutInflater li =  (LayoutInflater) MA.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View wrapperView = null;

        assert li != null;

        orderItems.updateOrderItems();

        for (int i = 0, ix = cartItems.size(); i < ix;  i++) {

            if (i % 2 == 0) {

                wrapperView = li.inflate(R.layout.menu_item_view_wrapper, container, false);
                mainLayout.addView(wrapperView);

            }

            String id = cartItems.get(i).getId();
            CartItem orderItem = MA.orderedItemsList.get(id);

            if(orderItem != null)

                cartItems.get(i).setQuantity(orderItem.getQuantity());

            LinearLayout wrapperLayout = wrapperView.findViewWithTag("itemMenuWrapper");
            View itemView = li.inflate(R.layout.menu_item_view, container, false);
            ImageView itemImg = itemView.findViewWithTag("itemImg");
            TextView itemTitle = itemView.findViewWithTag("itemTitle");
            TextView itemPrice = itemView.findViewWithTag("itemPrice");
            ImageView itemAdd = itemView.findViewWithTag("itemAdd");
            final ImageView itemRemove = itemView.findViewWithTag("itemRemove");
            final TextView itemQuantity = itemView.findViewWithTag("itemQuantity");
            final int finalI = i;
            int quantity = cartItems.get(i).getQuantity();
            LinearLayout itemContainer = itemView.findViewWithTag("itemContainer");

            if (i % 2 == 0)
                itemContainer.setPadding(10,10,5,10);
            else
                itemContainer.setPadding(5,10,10,10);

            int res = getResources().getIdentifier(
                cartItems.get(i).getImg(),
                "drawable",
                this.requireContext().getPackageName());

            if (res != 0)

                itemImg.setImageResource(res);

            itemImg.setClipToOutline(true);

            itemTitle.setText(cartItems.get(i).getTitle());
            String text = "$" + Math.round(cartItems.get(i).getPrice());
            itemPrice.setText(text);

            if (quantity > 0) {
                itemQuantity.setText(String.valueOf(quantity));
                itemRemove.setVisibility(View.VISIBLE);
                itemQuantity.setVisibility(View.VISIBLE);
            }
            itemView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1.0f));

            itemAdd.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    itemRemove.setVisibility(View.VISIBLE);
                    itemQuantity.setVisibility(View.VISIBLE);
                    cartItems.get(finalI).setQuantity(cartItems.get(finalI).getQuantity() + 1);
                    itemQuantity.setText(String.format(Locale.getDefault(), "%d",cartItems.get(finalI).getQuantity()));
                    CartItem item = cartItems.get(finalI);
                    MA.orderedItemsList.put(item.getId(), item);
                    orderItems.updateOrderItems();

                }

            } );

            itemRemove.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    int quantity = cartItems.get(finalI).getQuantity() - 1;

                    if (quantity == 0) {

                        itemRemove.setVisibility(View.INVISIBLE);
                        itemQuantity.setVisibility(View.INVISIBLE);

                    }

                    cartItems.get(finalI).setQuantity(quantity);
                    itemQuantity.setText(String.format(Locale.getDefault(), "%d",cartItems.get(finalI).getQuantity()));
                    CartItem item = cartItems.get(finalI);
                    MA.orderedItemsList.put(item.getId(), item);
                    if (quantity == 0)
                        MA.orderedItemsList.remove(item.getId());
                    orderItems.updateOrderItems();

                    }

            } );

            wrapperLayout.addView(itemView);


            //}

        }

        view.findViewById(R.id.orderItemContainer).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                NavHostFragment.findNavController(ItemMenu.this).navigate(R.id.action_SecondFragment_to_ThirdFragment);

            }

        } );

 //       toolbar.findViewById(R.id.button_back).setVisibility(View.VISIBLE);
   //     toolbar.findViewById(R.id.button_back).setOnClickListener(new View.OnClickListener(){
      //      @Override
        //    public void onClick(View view) {
     //           NavHostFragment.findNavController(ItemMenu.this).navigate(R.id.action_SecondFragment_to_FirstFragment);
//
  //          }
    //    });
        return view;
    }

}

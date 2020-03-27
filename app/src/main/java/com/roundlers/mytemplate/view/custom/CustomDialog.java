package com.roundlers.mytemplate.view.custom;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.roundlers.mytemplate.R;
import com.roundlers.mytemplate.helper.AppHelper;
import com.roundlers.mytemplate.helper.EventbusHelper;
import com.roundlers.mytemplate.models.events.DismissPopup;

/**
 * Created by faheem on 09/11/17.
 */
public class CustomDialog extends Dialog {
    private CustomDialogBuilder customDialogBuilder;
    private Context context;
    private View parent;
    private EditText editText;
    private RadioGroup radioGroup;
    private RadioButton topRadioButton, bottomRadioBtn;

    private CustomDialog(Context context, CustomDialogBuilder customDialogBuilder) {
        super(context, R.style.PopUpBackgroundStyle);
        this.context = context;
        this.customDialogBuilder = customDialogBuilder;
        this.requestWindowFeature(1);
        View view = this.getView();
        this.setContentView(view);
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                EventbusHelper.post(new DismissPopup());
            }
        });
        setViews(view);
    }

    private View getView() {
        return View.inflate(this.context, R.layout.custom_dialog, null);
    }

    public void setViews(View view) {
        TextView title = view.findViewById(R.id.title);
        TextView description = view.findViewById(R.id.description);
        TextView topButton = view.findViewById(R.id.topBtn);
        TextView bottomButton = view.findViewById(R.id.bottomBtn);
        editText = view.findViewById(R.id.editText);
        topRadioButton = view.findViewById(R.id.rb1);
        parent = view.findViewById(R.id.parent);
        bottomRadioBtn = view.findViewById(R.id.rb2);
        radioGroup = view.findViewById(R.id.radioGroup);
        ImageView imageView = view.findViewById(R.id.image);
        ImageView imageView_bottom = view.findViewById(R.id.image_bottom);
        FrameLayout middleView = view.findViewById(R.id.middle_view);
        ImageView topRightImage = view.findViewById(R.id.image_top_right);

        TextView topLeftBtn = view.findViewById(R.id.topLeftBtn);

        //set text values
        title.setText(this.customDialogBuilder.titleTxt);
        description.setText(this.customDialogBuilder.descriptionTxt);
        topButton.setText(this.customDialogBuilder.topBtnText);
        bottomButton.setText(this.customDialogBuilder.bottomBtnText);
        topLeftBtn.setText(this.customDialogBuilder.topLeftBtnText);

        bottomRadioBtn.setText(this.customDialogBuilder.bottomRadioBtnTxt);
        topRadioButton.setText(this.customDialogBuilder.topRadioBtnTxt);
        bottomRadioBtn.setText(this.customDialogBuilder.bottomRadioBtnTxt);
        topRadioButton.setText(this.customDialogBuilder.topRadioBtnTxt);

        if (this.customDialogBuilder.image != 0) {
            imageView.setBackgroundDrawable(context.getResources().getDrawable(this.customDialogBuilder.image));
        }

        if (this.customDialogBuilder.bitmap != null) {
            imageView_bottom.setImageBitmap(this.customDialogBuilder.bitmap);
        }

        if (this.customDialogBuilder.middleView != null) {
            middleView.addView(this.customDialogBuilder.middleView);
        }

        //set Visibilities
        if (customDialogBuilder.middleViewVisibility) {
            middleView.setVisibility(View.VISIBLE);
        }
        if (customDialogBuilder.topRightImageVisibility) {
            topRightImage.setVisibility(View.VISIBLE);
        }
        if (customDialogBuilder.titleVisibility) {
            title.setVisibility(View.VISIBLE);
        }
        if (customDialogBuilder.descriptionVisibility) {
            description.setVisibility(View.VISIBLE);
        }
        if (customDialogBuilder.editTextVisibility) {
            editText.setVisibility(View.VISIBLE);
        }
        if (customDialogBuilder.topBtnVisibility) {
            topButton.setVisibility(View.VISIBLE);
        }
        if (customDialogBuilder.bottomBtnVisibility) {
            bottomButton.setVisibility(View.VISIBLE);
        }
        if (customDialogBuilder.editTextVisibility) {
            editText.setVisibility(View.VISIBLE);
        }
        if (customDialogBuilder.imageVisibility) {
            imageView.setVisibility(View.VISIBLE);
        }
        if (customDialogBuilder.imageBottomVisibility) {
            imageView_bottom.setVisibility(View.VISIBLE);
        }
        if (customDialogBuilder.topLeftBtnVisibility) {
            topLeftBtn.setVisibility(View.VISIBLE);
        }
        if (customDialogBuilder.editTextVisibility) {
            editText.setVisibility(View.VISIBLE);
        }
        if (customDialogBuilder.radioGroupVisibility) {
            radioGroup.setVisibility(View.VISIBLE);
        }
        if (customDialogBuilder.bottomRadioBtnVisibility) {
            bottomRadioBtn.setVisibility(View.VISIBLE);
            radioGroup.setVisibility(View.VISIBLE);
        }
        if (customDialogBuilder.topRadioBtnVisibility) {
            topRadioButton.setVisibility(View.VISIBLE);
            radioGroup.setVisibility(View.VISIBLE);
        }
        if (customDialogBuilder.editTextHint != null) {
            editText.setHint(customDialogBuilder.editTextHint);
        }
        if (customDialogBuilder.editTextInputType != null) {
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        }

        editText.setFocusable(customDialogBuilder.setEditTextFocussable);
        editText.setFocusableInTouchMode(customDialogBuilder.setEditTextFocussable);
        editText.setClickable(customDialogBuilder.setEditTextFocussable);


        if (customDialogBuilder.biggerEditText) {
            editText.setLines(5);
            //editText.setSingleLine(false);
            editText.setGravity(Gravity.TOP);
            editText.setMinLines(5);
            editText.setMaxLines(8);
        }
        if (customDialogBuilder.checkedRadioBtntext != null) {
            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                /*if (((RadioButton) radioGroup.getChildAt(i)).getText().toString().equalsIgnoreCase(customDialogBuilder.checkedRadioBtntext)) {
                    ((RadioButton) radioGroup.getChildAt(i)).setChecked(true);
                } else*/
                if (((RadioButton) radioGroup.getChildAt(i)).getText().toString().equalsIgnoreCase("हिंदी") && customDialogBuilder.checkedRadioBtntext.equalsIgnoreCase("hi")) {
                    ((RadioButton) radioGroup.getChildAt(i)).setChecked(true);
                    break;
                } else if (((RadioButton) radioGroup.getChildAt(i)).getText().toString().equalsIgnoreCase("English") && customDialogBuilder.checkedRadioBtntext.equalsIgnoreCase("en")) {
                    ((RadioButton) radioGroup.getChildAt(i)).setChecked(true);
                    break;
                }
            }
        }
        //setOnclickListeners
//        AppHelper.setBackground(topButton, R.drawable.card_ripple_drawable, view.getContext(), co.gradeup.android.R.drawable.alternate_card_background);
//        AppHelper.setBackground(bottomButton, R.drawable.card_ripple_drawable, view.getContext(), co.gradeup.android.R.drawable.alternate_card_background);
//        AppHelper.setBackground(topLeftBtn, R.drawable.card_ripple_drawable, view.getContext(), co.gradeup.android.R.drawable.alternate_card_background);

        topButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // BackgroundHelper.setBtnRippleForeground(view, R.drawable.xml_ripple_btn, context, 0);
                if (customDialogBuilder.dialogClickListenerInterface != null) {
                    customDialogBuilder.dialogClickListenerInterface.onTopBtnClick();
                }
                if (customDialogBuilder.dismissByDefault) {
                    CustomDialog.this.dismiss();
                }
                else
                {
                    parent.setVisibility(View.GONE);
                }
            }
        });

        bottomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // BackgroundHelper.setBtnRippleForeground(view, R.drawable.xml_ripple_btn, context, 0);

                if (customDialogBuilder.dialogClickListenerInterface != null) {
                    customDialogBuilder.dialogClickListenerInterface.onBottomBtnClick();
                }
                if (customDialogBuilder.dismissByDefault) {
                    CustomDialog.this.dismiss();
                }
            }
        });


        topLeftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // BackgroundHelper.setBtnRippleForeground(view, R.drawable.xml_ripple_btn, context, 0);

                if (customDialogBuilder.dialogClickListenerInterface != null) {
                    customDialogBuilder.dialogClickListenerInterface.onTopLeftBtnClick();
                }
                if (customDialogBuilder.dismissByDefault) {
                    CustomDialog.this.dismiss();
                }
            }
        });

        topRightImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (customDialogBuilder.dialogClickListenerInterface != null) {
                    customDialogBuilder.dialogClickListenerInterface.onTopRightImageClicked();
                }
                CustomDialog.this.dismiss();
            }
        });


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                customDialogBuilder.dialogClickListenerInterface.onTextEntered(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    //Builder Class
    public static class CustomDialogBuilder {
        String topBtnText, bottomBtnText, titleTxt, descriptionTxt, topLeftBtnText, topRadioBtnTxt, bottomRadioBtnTxt;
        Context context;
        boolean titleVisibility, topBtnVisibility, bottomBtnVisibility, editTextVisibility, descriptionVisibility, radioGroupVisibility, imageVisibility, imageBottomVisibility, topLeftBtnVisibility, topRadioBtnVisibility, bottomRadioBtnVisibility, middleViewVisibility, topRightImageVisibility;
        int image;
        Bitmap bitmap;
        View middleView;
        String editTextInputType;
        String editTextHint;
        private DialogClickListenerInterface dialogClickListenerInterface;
        private String checkedRadioBtntext;
        private boolean biggerEditText;
        private boolean setEditTextFocussable = true;
        private boolean dismissByDefault = true;

        public CustomDialogBuilder(Context context) {
            this.context = context;
        }

        public CustomDialogBuilder setTopBtnText(String topBtnText) {
            this.topBtnText = topBtnText;
            topBtnVisibility = true;
            return this;
        }

        public CustomDialogBuilder setBiggerEditText(boolean biggerEditText) {
            this.biggerEditText = biggerEditText;

            return this;
        }

        public CustomDialogBuilder setTopRightImageVisibility(boolean visibility) {
            this.topRightImageVisibility = visibility;
            return this;
        }

        public CustomDialogBuilder setBottomBtnText(String bottomBtnText) {
            this.bottomBtnText = bottomBtnText;
            bottomBtnVisibility = true;
            return this;
        }

        public CustomDialogBuilder setEditTextInputType(String inputType) {
            this.editTextInputType = inputType;
            return this;
        }

        public CustomDialogBuilder setEditTextHint(String edittextHint) {
            this.editTextHint = edittextHint;
            return this;
        }

        public CustomDialogBuilder setTitleText(String titleText) {
            this.titleTxt = titleText;
            titleVisibility = true;
            return this;
        }

        public CustomDialogBuilder setDescriptionText(String descriptionText) {
            this.descriptionTxt = descriptionText;
            if (descriptionText == null || descriptionText.length() == 0) {
                descriptionVisibility = false;
                return this;


            }
            descriptionVisibility = true;
            return this;
        }

        public CustomDialogBuilder setTopLeftBtnText(String topLeftBtnText) {
            this.topLeftBtnText = topLeftBtnText;
            this.topLeftBtnVisibility = true;
            return this;
        }

        public CustomDialogBuilder setTopRadioButtonText(String topRadioBtnText) {
            this.topRadioBtnTxt = topRadioBtnText;
            this.topRadioBtnVisibility = true;
            this.radioGroupVisibility = true;
            return this;

        }

        public CustomDialogBuilder setBottomRadioButtonText(String bottomRadioBtnText) {
            this.bottomRadioBtnTxt = bottomRadioBtnText;
            this.bottomRadioBtnVisibility = true;
            this.radioGroupVisibility = true;
            return this;
        }

        public CustomDialogBuilder setEditTextVisibility(boolean visibility) {
            this.editTextVisibility = visibility;
            return this;
        }

        public CustomDialogBuilder setImage(int image) {
            this.image = image;
            this.imageVisibility = true;
            return this;
        }

        public CustomDialogBuilder setBottomImage(Bitmap image) {
            this.bitmap = image;
            this.imageBottomVisibility = true;
            return this;
        }

        public CustomDialogBuilder setMiddleView(View view) {
            this.middleView = view;
            this.middleViewVisibility = true;
            return this;
        }

        public CustomDialogBuilder setOnClickListeners(DialogClickListenerInterface dialogClickListenerInterface) {
            this.dialogClickListenerInterface = dialogClickListenerInterface;
            return this;
        }

        public CustomDialog build() {
            AppHelper.dieIfNull(context);
            return new CustomDialog(context, this);
        }

        public CustomDialogBuilder checkedBtnText(String languagePreference) {
            this.checkedRadioBtntext = languagePreference;
            return this;
        }

        public CustomDialogBuilder setEditTextFocussable(boolean setFocussable) {
            this.setEditTextFocussable = setFocussable;
            return this;
        }

        public CustomDialogBuilder setDismissByDefault(boolean dismissByDefault) {
            this.dismissByDefault = dismissByDefault;
            return this;
        }
    }

    public String getSelectedLanguage() {
        int radioId = radioGroup.getCheckedRadioButtonId();
        RadioButton rb = radioGroup.findViewById(radioId);
        return rb.getText().toString();

    }


}

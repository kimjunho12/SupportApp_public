package com.example.myapplication.register;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class RegisterActivity<mWebView> extends AppCompatActivity {
    private static final String TAG = "RegisterPage";
    private static final int TARGET = 1;
    private boolean is_signuped = false;
    private ImageButton btn_back;
    private Button btn_register_save;
    private CheckBox cb_target;
    private Intent intent;
    private EditText et_register_id, et_register_pw, et_register_pw_check, et_register_name, et_register_phone, et_register_birth, et_register_no_check;
    private Button btn_check_id, btn_check_phone, btn_check_submit;

    private FirebaseAuth mAuth;
    private String mVerificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private String smsCode = null;

    private String email;
    private String password;
    private String pw_check;
    private String name;
    private String phone;
    private String birthday;

    boolean is_id_checked = false;
    boolean is_phone_checked = false;
    private User user;

    private CheckBox cb_agree_all;
    private CheckBox cb_agree_use;
    private CheckBox cb_agreement_my;
    private CheckBox cb_agreement_marketing;

    private Button btn_arg;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Intent intent = getIntent();

        init();
        if (intent.getExtras() != null) {
            FirebaseUser user = (FirebaseUser) intent.getExtras().get("userInfo");
            // 받아온 값 바로 넘겨주고 잠궈버리기

            et_register_id.setText(user.getEmail());
            et_register_id.setEnabled(false);

            et_register_name.setText(user.getDisplayName());
        }

        btn_check_id.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Query mQuery = FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("id").equalTo(String.valueOf(et_register_id.getText()));
                mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        Log.d(TAG, "onDataChange: " + snapshot.getValue());
                        if (snapshot.getValue() != null) {
                            Toast.makeText(RegisterActivity.this, "아이디가 중복 되었습니다.\n다시 입력해주세요.", Toast.LENGTH_SHORT).show();
                            et_register_id.requestFocus();
                        } else {
                            if (TextUtils.isEmpty(et_register_id.getText())) {
                                return;
                            }
                            Toast.makeText(RegisterActivity.this, "사용가능한 아이디 입니다.", Toast.LENGTH_SHORT).show();
                            et_register_id.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_checked, 0);
                            et_register_id.setEnabled(false);
                            btn_check_id.setEnabled(false);
                            is_id_checked = true;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                        Log.e(TAG, "onCancelled: ", error.toException());
                    }
                });
            }
        });

        // Initialize phone auth callbacks
        // [START phone_auth_callbacks]
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);
                Log.d(TAG, "onCodeSent: " + token);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
            }

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:" + credential);
                Log.d(TAG, "onVerificationCompleted: " + credential.getSmsCode());

                smsCode = credential.getSmsCode();
                btn_check_phone.setText("문자 재전송");
                et_register_no_check.setVisibility(View.VISIBLE);
                btn_check_submit.setVisibility(View.VISIBLE);
                et_register_no_check.requestFocus();
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(RegisterActivity.this, "휴대폰 번호를 확인해 주세요", Toast.LENGTH_SHORT).show();
                    et_register_phone.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.design_default_color_error)));
                    et_register_phone.requestFocus();
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Toast.makeText(RegisterActivity.this, "인증 가능 횟수를 초과하였습니다\n잠시 후에 다시 시도해 주세요", Toast.LENGTH_SHORT).show();
                }
                // Show a message and update the UI
            }
        };
        // [END phone_auth_callbacks]

        // 인증번호 요청
        btn_check_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPhoneNumberVerification("+82" + String.valueOf(et_register_phone.getText()));
            }
        });

        // 인증번호 검증
        btn_check_submit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if (String.valueOf(et_register_no_check.getText()).equals(smsCode)) {
                    Toast.makeText(RegisterActivity.this, "인증되었습니다", Toast.LENGTH_SHORT).show();
                    et_register_phone.setEnabled(false);
                    et_register_no_check.setEnabled(false);
                    et_register_phone.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_checked, 0);
                    btn_check_submit.setEnabled(false);
                    btn_check_phone.setEnabled(false);
                    is_phone_checked = true;
                    btn_check_submit.setText("인증 완료");
                } else {

                    Toast.makeText(RegisterActivity.this, "인증번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                    et_register_no_check.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.design_default_color_error)));
                    et_register_no_check.requestFocus();
                }
                Log.d(TAG, "submit.onClick: " + smsCode);
            }
        });

        // 회원가입 양식 제출
        btn_register_save.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                email = String.valueOf(et_register_id.getText());
                password = String.valueOf(et_register_pw.getText());
                pw_check = String.valueOf(et_register_pw_check.getText());
                name = String.valueOf(et_register_name.getText());
                phone = String.valueOf(et_register_phone.getText());
                birthday = String.valueOf(et_register_birth.getText());
                boolean OK = true;

                // Phone & ID 중복확인 수행 결과 로직 추가 필요
                // is_id_checked, is_phone_checked 홯용

                if (!is_id_checked) {
                    Toast.makeText(RegisterActivity.this, "아이디 중복 확인을 해주세요", Toast.LENGTH_SHORT).show();
                    OK = false;
                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(RegisterActivity.this, "아이디를 정확하게 입력해주세요", Toast.LENGTH_SHORT).show();
                    et_register_id.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.design_default_color_error)));
                    et_register_id.requestFocus();
                    OK = false;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(RegisterActivity.this, "비밀번호를 정확하게 입력해주세요", Toast.LENGTH_SHORT).show();
                    et_register_pw.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.design_default_color_error)));
                    et_register_pw.requestFocus();
                    OK = false;
                }

                if (!password.equals(pw_check)) {
                    Toast.makeText(RegisterActivity.this, "비밀번호를 확인해 주세요", Toast.LENGTH_SHORT).show();
                    et_register_pw_check.setText(null);
                    et_register_pw.setText(null);
                    et_register_pw_check.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.design_default_color_error)));
                    et_register_pw.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.design_default_color_error)));
                    et_register_pw.requestFocus();
                    OK = false;
                }

                if (TextUtils.isEmpty(name)) {
                    et_register_name.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.design_default_color_error)));
                    et_register_name.requestFocus();
                    OK = false;
                }

                if (TextUtils.isEmpty(birthday)) {
                    et_register_birth.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.design_default_color_error)));
                    et_register_birth.requestFocus();
                    OK = false;
                }

                // SNS 로그인은 createAccount 안됨 -> 통합 필요
                if (OK && mAuth.getCurrentUser() == null) {
                    createAccount(email, password);
                }

                if (OK && is_signuped) {
                    moveToTargetDetailsActivity();
                }

                if (OK && mAuth.getCurrentUser() != null && !is_signuped) {
                    AuthCredential credential = EmailAuthProvider.getCredential(email, password);
                    linkAccount(credential);
                }

            }
        });
        //전체 동의
        CheckBox checkBox = findViewById(R.id.cb_agreement_all);
        //이용약관동의
        CheckBox checkBox1 = findViewById(R.id.cb_agreement_use);
        //개인정보 동의
        CheckBox checkBox2 = findViewById(R.id.cb_agreement_my);
        //마케팅 정보 수신동의
        CheckBox checkBox3 = findViewById(R.id.cb_agreement_marketing);
        //전체동의 클릭시
        checkBox.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked()) {
                    checkBox1.setChecked(true);
                    checkBox2.setChecked(true);
                    checkBox3.setChecked(true);
                }else{
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(false);
                }

            }
        });
        //이용약관동의 클릭시
        checkBox1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked()) {
                    checkBox.setChecked(false);
                }else if(checkBox1.isChecked()&&checkBox2.isChecked()&&checkBox3.isChecked()){
                checkBox.setChecked(true);
                }

            }
        });
        // 개인정보
        checkBox2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked()) {
                    checkBox.setChecked(false);
                }else if(checkBox1.isChecked()&&checkBox2.isChecked()&&checkBox3.isChecked()){
                    checkBox.setChecked(true);
                }

            }
        });
        //마케팅
        checkBox3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked()) {
                    checkBox.setChecked(false);
                }else if(checkBox1.isChecked()&&checkBox2.isChecked()&&checkBox3.isChecked()){
                    checkBox.setChecked(true);
                }

            }
        });
        Button btn_arg = findViewById(R.id.btn_arg);
        btn_arg.setText(R.string.underlined_text);
        btn_arg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle("[꽈배기 회원 약관]");
                builder.setMessage("[꽈배기 온라인 회원 약관]\n" +
                        "이 약관은 꽈배기  (이하 '꽈배기')에서 제공하는 서비스 이용조건 및 절차에 관한 사항과 기타 필요한 사항을 전기통신사업법 및 동법 시행령이 정하는 대로 준수하고 규정함을 목적으로 합니다\n" +
                        "\n" +
                        "제1조(목적 등)\n" +
                        "\n" +
                        "① 본원 (꽈배기) 이용자 약관(이하 \"본 약관\"이라 합니다)은 이용자가 본원에서 제공하는 인터넷 관련 서비스(이하 \"서비스\"라 합니다)를 이용함에 있어 회원과 본원의 권리·의무 및 책임사항을 규정함을 목적으로 합니다.\n" +
                        "② 회원이 되고자 하는 자가 본원에서 정한 소정의 절차를 거쳐서 \"등록하기\" 단추를 누르면 본 약관에 동의하는 것으로 간주합니다. 본 약관에 정하는 이외의 회원과 본원의 권리, 의무 및 책임사항에 관해서는 전기통신사업법 기타 대한민국의 관련 법령과 상관습에 의합니다.\n" +
                        "\n" +
                        "제2조(회원의 정의)\n" +
                        "\n" +
                        "\"회원\"이란 본원에 접속하여 본 약관에 따라 본원 온라인 회원으로 가입하여 본원이 제공하는 서비스를 받는 자를 말합니다.\n" +
                        "\n" +
                        "제3조 (회원 가입)\n" +
                        "\n" +
                        "① 회원이 되고자 하는 자는 본원이 정한 가입 양식에 따라 회원정보를 기입하고 \"등록하기\" 단추를 누르는 방법으로 회원 가입을 신청합니다.\n" +
                        "② 본원은 제1항과 같이 회원으로 가입할 것을 신청한 자가 다음 각 호에 해당하지 않는 한 신청한 자를 회원으로 등록합니다.\n" +
                        "1. 가입신청자가 본 약관 제6조 제3항에 의하여 이전에 회원자격을 상실한 적이 있는 경우. 다만 제6조 제3항에 의한 회원자격 상실 후 3년이 경과한 자로서 본원의 회원 재가입 승낙을 얻은 경우에는 예외로 합니다.\n" +
                        "2. 등록 내용에 허위, 기재누락, 오기가 있는 경우\n" +
                        "3. 기타 회원으로 등록하는 것이 본원의 기술상 현저히 지장이 있다고 판단되는 경우\n" +
                        "③ 회원가입계약의 성립시기는 본원의 승낙이 가입신청자에게 도달한 시점으로 합니다.\n" +
                        "④ 회원은 제1항의 회원정보 기재 내용에 변경이 발생한 경우, 즉시 변경사항을 정정하여 기재하여야 합니다.\n" +
                        "\n" +
                        "제4조(서비스의 제공 및 변경)\n" +
                        "\n" +
                        "① 본원은 회원에게 아래와 같은 서비스를 제공합니다.\n" +
                        "1. 본원 뉴스 메일 서비스\n" +
                        "2. 본원 온라인 상담실 / 예약 이용 서비스\n" +
                        "3. 본원 온라인 회원을 위한 섹션 및 컨텐츠 서비스\n" +
                        "4. 기타 본원이 타 업체와 제휴해서 제공하는 각종 서비스\n" +
                        "② 본원은 그 변경될 서비스의 내용 및 제공일자를 제7조 제2항에서 정한 방법으로 회원에게 통지하고, 제1항에 정한 서비스를 변경하여 제공할 수 있습니다.\n" +
                        "\n" +
                        "제5조(서비스의 중단)\n" +
                        "\n" +
                        "① 본원은 컴퓨터 등 정보통신설비의 보수점검·교체 및 고장, 통신의 두절 등의 사유가 발생한 경우에는 서비스의 제공을 일시적으로 중단할 수 있고, 새로운 서비스로의 교체 기타 본원이 적절하다고 판단하는 사유에 기하여 현재 제공되는 서비스를 완전히 중단할 수 있습니다.\n" +
                        "② 제1항에 의한 서비스 중단의 경우에는 본원은 제7조 제2항에서 정한 방법으로 회원에게 통지합니다. 다만, 본원이 통제할 수 없는 사유로 인한 서비스의 중단(시스템 관리자의 고의, 과실이 없는 디스크 장애, 시스템 다운 등)으로 인하여 사전 통지가 불가능한 경우에는 그러하지 아니합니다.\n" +
                        "\n" +
                        "제6조(회원 탈퇴 및 자격 상실 등)\n" +
                        "\n" +
                        "① 회원은 본원에 언제든지 자신의 회원 등록을 말소해 줄 것(회원 탈퇴)을 요청할 수 있으며, 본원은 위 요청을 받은 즉시 해당 회원의 회원 등록 말소를 위한 절차를 밟습니다.\n" +
                        "② 회원이 다음 각 호의 사유에 해당하는 경우, 본원은 회원의 회원자격을 적절한 방법으로 제한 및 정지, 상실시킬 수 있습니다.\n" +
                        "1. 가입 신청 시에 허위 내용을 등록한 경우\n" +
                        "2. 다른 사람의 본원 사이트 이용을 방해하거나 그 정보를 도용하는 등 전자거래질서를 위협하는 경우\n" +
                        "3. 본원을 이용하여 법령과 본 약관이 금지하거나 공서양속에 반하는 행위를 하는 경우\n" +
                        "③ 본원이 회원의 회원자격을 상실시키기로 결정한 경우에는 회원등록을 말소합니다. 이 경우 회원인 회원에게 회원등록 말소 전에 이를 통지하고, 소명할 기회를 부여합니다.\n" +
                        "\n" +
                        "제7조(회원에 대한 통지)\n" +
                        "\n" +
                        "① 본원이 특정 회원에 대한 통지를 하는 경우 본원에 등록한 메일주소로 할 수 있습니다.\n" +
                        "② 본원이 불특정다수 회원에 대한 통지를 하는 경우 1주일이상 본원 게시판에 게시함으로써 개별 통지에 갈음할 수 있습니다.\n" +
                        "\n" +
                        "제8조(회원의 개인정보보호)\n" +
                        "\n" +
                        "본원은 관련법령이 정하는 바에 따라서 회원 등록정보를 포함한 회원의 개인정보를 보호하기 위하여 노력합니다. 회원의 개인정보보호에 관해서는 관련법령 및 본원이 정하는 \"개인정보취급방침\"에 정한 바에 의합니다.\n" +
                        "\n" +
                        "제9조(본원의 의무)\n" +
                        "\n" +
                        "① 본원은 법령과 본 약관이 금지하거나 공서양속에 반하는 행위를 하지 않으며 본 약관이 정하는 바에 따라 지속적이고, 안정적으로 서비스를 제공하기 위해서 노력합니다.\n" +
                        "② 본원은 회원이 안전하게 인터넷 서비스를 이용할 수 있도록 회원의 개인정보(신용정보포함)보호를 위한 보안 시스템을 구축합니다.\n" +
                        "③ 본원은 회원이 원하지 않는 영리목적의 광고성 전자우편을 발송하지 않습니다.\n" +
                        "\n" +
                        "제10조(회원의 ID 및 비밀번호에 대한 의무)\n" +
                        "\n" +
                        "① 본원이 관계법령, \"개인정보보호정책\"에 의해서 그 책임을 지는 경우를 제외하고, 자신의 ID와 비밀번호에 관한 관리책임은 각 회원에게 있습니다.\n" +
                        "② 회원은 자신의 ID 및 비밀번호를 제3자에게 이용하게 해서는 안됩니다.\n" +
                        "③ 회원은 자신의 ID 및 비밀번호를 도난당하거나 제3자가 사용하고 있음을 인지한 경우에는 바로 본원에 통보하고 본원의 안내가 있는 경우에는 그에 따라야 합니다.\n" +
                        "\n" +
                        "제11조(회원의 의무)\n" +
                        "\n" +
                        "① 회원은 다음 각 호의 행위를 하여서는 안됩니다.\n" +
                        "1. 회원가입신청 또는 변경 시 허위내용을 등록하는 행위\n" +
                        "2. 본원에 게시된 정보를 변경하는 행위\n" +
                        "3. 본원 기타 제3자의 인격권 또는 지적재산권을 침해하거나 업무를 방해하는 행위\n" +
                        "4. 다른 회원의 ID를 도용하는 행위\n" +
                        "5. 정크메일(junk mail), 스팸메일(spam mail), 행운의 편지(chain letters), 피라미드 조직에 가입할 것을 권유하는 메일, 외설 또는 폭력적인 메시지·화상·음성 등이 담긴 메일을 보내거나 기타 공서양속에 반하는 정보를 공개 또는 게시하는 행위.\n" +
                        "6. 관련 법령에 의하여 그 전송 또는 게시가 금지되는 정보(컴퓨터 프로그램 등)의 전송 또는 게시하는 행위\n" +
                        "7. 본원의 직원이나 본원 인터넷 서비스의 관리자를 가장하거나 사칭하여 또는 타인의 명의를 도용하여 글을 게시하거나 메일을 발송하는 행위\n" +
                        "8. 컴퓨터 소프트웨어, 하드웨어, 전기통신 장비의 정상적인 가동을 방해, 파괴할 목적으로 고안된 소프트웨어 바이러스, 기타 다른 컴퓨터 코드, 파일, 프로그램을 포함하고 있는 자료를 게시하거나 전자우편으로 발송하는 행위\n" +
                        "9. 스토킹(stalking) 등 다른 회원을 괴롭히는 행위\n" +
                        "10. 다른 회원에 대한 개인정보를 동의 없이 수집, 저장, 공개하는 행위\n" +
                        "11. 불특정 다수의 자를 대상으로 하여 광고 또는 선전을 게시하거나 스팸메일을 전송하는 등의 방법으로 본원의 서비스를 이용하여 영리목적의 활동을 하는 행위\n" +
                        "12. 본원이 제공하는 서비스에 정한 약관 기타 서비스 이용에 관한 규정을 위반하는 행위\n" +
                        "② 제1항에 해당하는 행위를 한 회원이 있을 경우 본원은 본 약관 제6조 제2, 3항에서 정한 바에 따라 회원의 회원자격을 적절한 방법으로 제한 및 정지, 상실시킬 수 있습니다.\n" +
                        "③ 회원은 그 귀책사유로 인하여 본원이나 다른 회원이 입은 손해를 배상할 책임이 있습니다.\n" +
                        "\n" +
                        "제 12조 (공개게시물의 삭제 및 비공개)\n" +
                        "\n" +
                        "회원의 공개게시물의 내용이 다음 각 호에 해당하는 경우 본원은 회원에게 사전 통지 없이 해당 공개게시물을 삭제할 수 있고, 해당 회원의 회원 자격을 제한, 정지 또는 상실시킬 수 있습니다.\n" +
                        "1. 다른 회원 또는 제3자를 비방하거나 중상 모략으로 명예를 손상시키는 내용\n" +
                        "2. 공서양속에 위반되는 내용의 정보, 문장, 도형 등을 유포하는 내용\n" +
                        "3. 범죄행위와 관련이 있다고 판단되는 내용\n" +
                        "4. 다른 회원 또는 제3자의 저작권 등 기타 권리를 침해하는 내용\n" +
                        "5. 광고성 또는 상업적 목적이 두드러진 경우\n" +
                        "6. 기타 관계 법령에 위배된다고 판단되는 내용\n" +
                        "\n" +
                        "제13조(저작권의 귀속 및 이용제한)\n" +
                        "\n" +
                        "① 본원이 작성한 저작물에 대한 저작권 기타 지적재산권은 본원에 귀속합니다.\n" +
                        "② 회원은 본원을 이용함으로써 얻은 정보를 본원의 사전승낙 없이 복제, 전송, 출판, 배포, 방송 기타 방법에 의하여 영리목적으로 이용하거나 제3자에게 이용하게 하여서는 안됩니다.\n" +
                        "\n" +
                        "제14조(상담에 관한 규정)\n" +
                        "\n" +
                        "① 서비스에서 진행된 상담의 내용은 개인 신상정보를 삭제한 다음 아래와 같은 목적으로 사용할 수 있습니다.\n" +
                        "1) 학술활동\n" +
                        "2) 진료활동\n" +
                        "3) 의료서비스\n" +
                        "4) 인쇄물, CD-ROM 등의 저작활동\n" +
                        "5) FAQ, 추천상담 등의 서비스 내용의 일부\n" +
                        "② 아래와 같은 상담을 신청하는 경우에는 상담 서비스를 전체 또는 일부 제공하지 않을 수 있습니다.\n" +
                        "1) 같은 내용의 상담을 반복하여 신청하는 경우\n" +
                        "2) 상식에 어긋나는 표현을 사용하거나 비속어를 사용하여 상담을 신청하는 경우\n" +
                        "3) 진단명을 요구하는 상담을 신청하는 경우\n" +
                        "4) 치료비, 검사비, 의약품 가격, 의약품의 효과 등에 대하여 상담을 신청하는 경우\n" +
                        "5) 타인을 해하기 위한 정보 취득목적으로 상담하는 경우\n" +
                        "\n" +
                        "제15조(약관의 개정)\n" +
                        "\n" +
                        "① 본원은 약관의 규제 등에 관한 법률, 전자거래기본법, 전자서명법, 정보통신망 이용촉진 등에 관한 법률 등 관련법을 위배하지 않는 범위에서 본 약관을 개정할 수 있습니다.\n" +
                        "② 본원이 본 약관을 개정할 경우에는 적용일자 및 개정사유를 명시하여 현행약관과 함께 초기화면에 그 적용일자 7일 이전부터 적용일자 전일까지 공지합니다.\n" +
                        "③ 본원이 본 약관을 개정할 경우에는 그 개정약관은 개정된 내용이 관계 법령에 위배되지 않는 한 개정 이전에 회원으로 가입한 회원에게도 적용됩니다.\n" +
                        "④ 변경된 약관에 이의가 있는 회원은 제6조 제1항에 따라 탈퇴할 수 있습니다.\n" +
                        "\n" +
                        "제16조(재판관할)\n" +
                        "\n" +
                        "본원과 회원간에 발생한 서비스 이용에 관한 분쟁으로 인한 소는 민사소송법상의 관할을 가지는 대한민국의 법원에 제기합니다.\n" +
                        "\n" +
                        "부 칙\n" +
                        "① 이 약관은 2021년 9월 28일을 시작으로 새로운 약관이 나오기 전까지 사용합니다.");
                builder.setPositiveButton("아니오",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(),"체크박스에 동의해주세요.",Toast.LENGTH_LONG).show();
                            }
                        });
                builder.setNegativeButton("예",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(),"확인되었습니다.",Toast.LENGTH_LONG).show();

                                checkBox1.setChecked(true);
                            }
                        });

                builder.show();
            }
        });
    Button btn_arg1 = findViewById(R.id.btn_arg1);
        btn_arg1.setText(R.string.underlined_text);
        btn_arg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle("[꽈배기 회원 약관]");
                builder.setMessage("[꽈배기 회원 약관]\n" +
                        "이 약관은 꽈배기 (이하 '본원')에서 제공하는 서비스 이용조건 및 절차에 관한 사항과 기타 필요한 사항을 전기통신사업법 및 동법 시행령이 정하는 대로 준수하고 규정함을 목적으로 합니다\n" +
                        "\n" +
                        "제1조(목적 등)\n" +
                        "\n" +
                        "① 본원(꽈배기) 이용자 약관(이하 \"본 약관\"이라 합니다)은 이용자가 본원에서 제공하는 인터넷 관련 서비스(이하 \"서비스\"라 합니다)를 이용함에 있어 회원과 본원의 권리·의무 및 책임사항을 규정함을 목적으로 합니다.\n" +
                        "② 회원이 되고자 하는 자가 본원에서 정한 소정의 절차를 거쳐서 \"회원가입\" 단추를 누르면 본 약관에 동의하는 것으로 간주합니다. 본 약관에 정하는 이외의 회원과 본원의 권리, 의무 및 책임사항에 관해서는 전기통신사업법 기타 대한민국의 관련 법령과 상관습에 의합니다.\n" +
                        "\n" +
                        "제2조(회원의 정의)\n" +
                        "\n" +
                        "\"회원\"이란 본원에 접속하여 본 약관에 따라 본원 온라인 회원으로 가입하여 본원이 제공하는 서비스를 받는 자를 말합니다.\n" +
                        "\n" +
                        "제3조 (회원 가입)\n" +
                        "\n" +
                        "① 회원이 되고자 하는 자는 본원이 정한 가입 양식에 따라 회원정보를 기입하고 \"회원가입\" 단추를 누르는 방법으로 회원 가입을 신청합니다.\n" +
                        "② 본원은 제1항과 같이 회원으로 가입할 것을 신청한 자가 다음 각 호에 해당하지 않는 한 신청한 자를 회원으로 등록합니다.\n" +
                        "1. 가입신청자가 본 약관 제6조 제3항에 의하여 이전에 회원자격을 상실한 적이 있는 경우. 다만 제6조 제3항에 의한 회원자격 상실 후 3년이 경과한 자로서 본원의 회원 재가입 승낙을 얻은 경우에는 예외로 합니다.\n" +
                        "2. 등록 내용에 허위, 기재누락, 오기가 있는 경우\n" +
                        "3. 기타 회원으로 등록하는 것이 본원의 기술상 현저히 지장이 있다고 판단되는 경우\n" +
                        "③ 회원가입계약의 성립시기는 본원의 승낙이 가입신청자에게 도달한 시점으로 합니다.\n" +
                        "④ 회원은 제1항의 회원정보 기재 내용에 변경이 발생한 경우, 즉시 변경사항을 정정하여 기재하여야 합니다.\n" +
                        "\n" +
                        "제4조(서비스의 제공 및 변경)\n" +
                        "\n" +
                        "① 본원은 회원에게 아래와 같은 서비스를 제공합니다.\n" +
                        "1. 본원 뉴스 메일 서비스\n" +
                        "2. 본원 온라인 상담실 / 예약 이용 서비스\n" +
                        "3. 본원 온라인 회원을 위한 섹션 및 컨텐츠 서비스\n" +
                        "4. 기타 본원이 타 업체와 제휴해서 제공하는 각종 서비스\n" +
                        "② 본원은 그 변경될 서비스의 내용 및 제공일자를 제7조 제2항에서 정한 방법으로 회원에게 통지하고, 제1항에 정한 서비스를 변경하여 제공할 수 있습니다.\n" +
                        "\n" +
                        "제5조(서비스의 중단)\n" +
                        "\n" +
                        "① 본원은 컴퓨터 등 정보통신설비의 보수점검·교체 및 고장, 통신의 두절 등의 사유가 발생한 경우에는 서비스의 제공을 일시적으로 중단할 수 있고, 새로운 서비스로의 교체 기타 본원이 적절하다고 판단하는 사유에 기하여 현재 제공되는 서비스를 완전히 중단할 수 있습니다.\n" +
                        "② 제1항에 의한 서비스 중단의 경우에는 본원은 제7조 제2항에서 정한 방법으로 회원에게 통지합니다. 다만, 본원이 통제할 수 없는 사유로 인한 서비스의 중단(시스템 관리자의 고의, 과실이 없는 디스크 장애, 시스템 다운 등)으로 인하여 사전 통지가 불가능한 경우에는 그러하지 아니합니다.\n" +
                        "\n" +
                        "제6조(회원 탈퇴 및 자격 상실 등)\n" +
                        "\n" +
                        "① 회원은 본원에 언제든지 자신의 회원 등록을 말소해 줄 것(회원 탈퇴)을 요청할 수 있으며, 본원은 위 요청을 받은 즉시 해당 회원의 회원 등록 말소를 위한 절차를 밟습니다.\n" +
                        "② 회원이 다음 각 호의 사유에 해당하는 경우, 본원은 회원의 회원자격을 적절한 방법으로 제한 및 정지, 상실시킬 수 있습니다.\n" +
                        "1. 가입 신청 시에 허위 내용을 등록한 경우\n" +
                        "2. 다른 사람의 본원 사이트 이용을 방해하거나 그 정보를 도용하는 등 전자거래질서를 위협하는 경우\n" +
                        "3. 본원을 이용하여 법령과 본 약관이 금지하거나 공서양속에 반하는 행위를 하는 경우\n" +
                        "③ 본원이 회원의 회원자격을 상실시키기로 결정한 경우에는 회원등록을 말소합니다. 이 경우 회원인 회원에게 회원등록 말소 전에 이를 통지하고, 소명할 기회를 부여합니다.\n" +
                        "\n" +
                        "제7조(회원에 대한 통지)\n" +
                        "\n" +
                        "① 본원이 특정 회원에 대한 통지를 하는 경우 본원에 등록한 메일주소로 할 수 있습니다.\n" +
                        "② 본원이 불특정다수 회원에 대한 통지를 하는 경우 1주일이상 본원 게시판에 게시함으로써 개별 통지에 갈음할 수 있습니다.\n" +
                        "\n" +
                        "제8조(회원의 개인정보보호)\n" +
                        "\n" +
                        "본원은 관련법령이 정하는 바에 따라서 회원 등록정보를 포함한 회원의 개인정보를 보호하기 위하여 노력합니다. 회원의 개인정보보호에 관해서는 관련법령 및 본원이 정하는 \"개인정보취급방침\"에 정한 바에 의합니다.\n" +
                        "\n" +
                        "제9조(본원의 의무)\n" +
                        "\n" +
                        "① 본원은 법령과 본 약관이 금지하거나 공서양속에 반하는 행위를 하지 않으며 본 약관이 정하는 바에 따라 지속적이고, 안정적으로 서비스를 제공하기 위해서 노력합니다.\n" +
                        "② 본원은 회원이 안전하게 인터넷 서비스를 이용할 수 있도록 회원의 개인정보(신용정보포함)보호를 위한 보안 시스템을 구축합니다.\n" +
                        "③ 본원은 회원이 원하지 않는 영리목적의 광고성 전자우편을 발송하지 않습니다.\n" +
                        "\n" +
                        "제10조(회원의 ID 및 비밀번호에 대한 의무)\n" +
                        "\n" +
                        "① 본원이 관계법령, \"개인정보보호정책\"에 의해서 그 책임을 지는 경우를 제외하고, 자신의 ID와 비밀번호에 관한 관리책임은 각 회원에게 있습니다.\n" +
                        "② 회원은 자신의 ID 및 비밀번호를 제3자에게 이용하게 해서는 안됩니다.\n" +
                        "③ 회원은 자신의 ID 및 비밀번호를 도난당하거나 제3자가 사용하고 있음을 인지한 경우에는 바로 본원에 통보하고 본원의 안내가 있는 경우에는 그에 따라야 합니다.\n" +
                        "\n" +
                        "제11조(회원의 의무)\n" +
                        "\n" +
                        "① 회원은 다음 각 호의 행위를 하여서는 안됩니다.\n" +
                        "1. 회원가입신청 또는 변경 시 허위내용을 등록하는 행위\n" +
                        "2. 본원에 게시된 정보를 변경하는 행위\n" +
                        "3. 본원 기타 제3자의 인격권 또는 지적재산권을 침해하거나 업무를 방해하는 행위\n" +
                        "4. 다른 회원의 ID를 도용하는 행위\n" +
                        "5. 정크메일(junk mail), 스팸메일(spam mail), 행운의 편지(chain letters), 피라미드 조직에 가입할 것을 권유하는 메일, 외설 또는 폭력적인 메시지·화상·음성 등이 담긴 메일을 보내거나 기타 공서양속에 반하는 정보를 공개 또는 게시하는 행위.\n" +
                        "6. 관련 법령에 의하여 그 전송 또는 게시가 금지되는 정보(컴퓨터 프로그램 등)의 전송 또는 게시하는 행위\n" +
                        "7. 본원의 직원이나 본원 인터넷 서비스의 관리자를 가장하거나 사칭하여 또는 타인의 명의를 도용하여 글을 게시하거나 메일을 발송하는 행위\n" +
                        "8. 컴퓨터 소프트웨어, 하드웨어, 전기통신 장비의 정상적인 가동을 방해, 파괴할 목적으로 고안된 소프트웨어 바이러스, 기타 다른 컴퓨터 코드, 파일, 프로그램을 포함하고 있는 자료를 게시하거나 전자우편으로 발송하는 행위\n" +
                        "9. 스토킹(stalking) 등 다른 회원을 괴롭히는 행위\n" +
                        "10. 다른 회원에 대한 개인정보를 동의 없이 수집, 저장, 공개하는 행위\n" +
                        "11. 불특정 다수의 자를 대상으로 하여 광고 또는 선전을 게시하거나 스팸메일을 전송하는 등의 방법으로 본원의 서비스를 이용하여 영리목적의 활동을 하는 행위\n" +
                        "12. 본원이 제공하는 서비스에 정한 약관 기타 서비스 이용에 관한 규정을 위반하는 행위\n" +
                        "② 제1항에 해당하는 행위를 한 회원이 있을 경우 본원은 본 약관 제6조 제2, 3항에서 정한 바에 따라 회원의 회원자격을 적절한 방법으로 제한 및 정지, 상실시킬 수 있습니다.\n" +
                        "③ 회원은 그 귀책사유로 인하여 본원이나 다른 회원이 입은 손해를 배상할 책임이 있습니다.\n" +
                        "\n" +
                        "제 12조 (공개게시물의 삭제 및 비공개)\n" +
                        "\n" +
                        "회원의 공개게시물의 내용이 다음 각 호에 해당하는 경우 본원은 회원에게 사전 통지 없이 해당 공개게시물을 삭제할 수 있고, 해당 회원의 회원 자격을 제한, 정지 또는 상실시킬 수 있습니다.\n" +
                        "1. 다른 회원 또는 제3자를 비방하거나 중상 모략으로 명예를 손상시키는 내용\n" +
                        "2. 공서양속에 위반되는 내용의 정보, 문장, 도형 등을 유포하는 내용\n" +
                        "3. 범죄행위와 관련이 있다고 판단되는 내용\n" +
                        "4. 다른 회원 또는 제3자의 저작권 등 기타 권리를 침해하는 내용\n" +
                        "5. 광고성 또는 상업적 목적이 두드러진 경우\n" +
                        "6. 기타 관계 법령에 위배된다고 판단되는 내용\n" +
                        "\n" +
                        "제13조(저작권의 귀속 및 이용제한)\n" +
                        "\n" +
                        "① 본원이 작성한 저작물에 대한 저작권 기타 지적재산권은 본원에 귀속합니다.\n" +
                        "② 회원은 본원을 이용함으로써 얻은 정보를 본원의 사전승낙 없이 복제, 전송, 출판, 배포, 방송 기타 방법에 의하여 영리목적으로 이용하거나 제3자에게 이용하게 하여서는 안됩니다.\n" +
                        "\n" +
                        "제14조(상담에 관한 규정)\n" +
                        "\n" +
                        "① 서비스에서 진행된 상담의 내용은 개인 신상정보를 삭제한 다음 아래와 같은 목적으로 사용할 수 있습니다.\n" +
                        "1) 학술활동\n" +
                        "2) 진료활동\n" +
                        "3) 의료서비스\n" +
                        "4) 인쇄물, CD-ROM 등의 저작활동\n" +
                        "5) FAQ, 추천상담 등의 서비스 내용의 일부\n" +
                        "② 아래와 같은 상담을 신청하는 경우에는 상담 서비스를 전체 또는 일부 제공하지 않을 수 있습니다.\n" +
                        "1) 같은 내용의 상담을 반복하여 신청하는 경우\n" +
                        "2) 상식에 어긋나는 표현을 사용하거나 비속어를 사용하여 상담을 신청하는 경우\n" +
                        "3) 진단명을 요구하는 상담을 신청하는 경우\n" +
                        "4) 치료비, 검사비, 의약품 가격, 의약품의 효과 등에 대하여 상담을 신청하는 경우\n" +
                        "5) 타인을 해하기 위한 정보 취득목적으로 상담하는 경우\n" +
                        "\n" +
                        "제15조(약관의 개정)\n" +
                        "\n" +
                        "① 본원은 약관의 규제 등에 관한 법률, 전자거래기본법, 전자서명법, 정보통신망 이용촉진 등에 관한 법률 등 관련법을 위배하지 않는 범위에서 본 약관을 개정할 수 있습니다.\n" +
                        "② 본원이 본 약관을 개정할 경우에는 적용일자 및 개정사유를 명시하여 현행약관과 함께 초기화면에 그 적용일자 7일 이전부터 적용일자 전일까지 공지합니다.\n" +
                        "③ 본원이 본 약관을 개정할 경우에는 그 개정약관은 개정된 내용이 관계 법령에 위배되지 않는 한 개정 이전에 회원으로 가입한 회원에게도 적용됩니다.\n" +
                        "④ 변경된 약관에 이의가 있는 회원은 제6조 제1항에 따라 탈퇴할 수 있습니다.\n" +
                        "\n" +
                        "제16조(재판관할)\n" +
                        "\n" +
                        "본원과 회원간에 발생한 서비스 이용에 관한 분쟁으로 인한 소는 민사소송법상의 관할을 가지는 대한민국의 법원에 제기합니다.\n" +
                        "\n" +
                        "부 칙\n" +
                        "① 이 약관은 2021년 9월 28일을 시작으로 새로운 약관이 나오기 전까지 사용합니다.");
                builder.setPositiveButton("아니오",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(),"필수항목입니다.",Toast.LENGTH_LONG).show();
                                checkBox2.setChecked(true);
                            }
                        });
                builder.setNegativeButton("예",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(),"확인되었습니다.",Toast.LENGTH_LONG).show();
                                checkBox2.setChecked(true);
                            }
                        });
                builder.show();
            }
        });
        Button btn_arg2 = findViewById(R.id.btn_arg2);
        btn_arg2.setText(R.string.underlined_text);
        btn_arg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle("[꽈배기 회원 약관]");
                builder.setMessage("[꽈배기 온라인 회원 약관]\n" +
                        "이 약관은 꽈배기 (이하 '본원')에서 제공하는 서비스 이용조건 및 절차에 관한 사항과 기타 필요한 사항을 전기통신사업법 및 동법 시행령이 정하는 대로 준수하고 규정함을 목적으로 합니다\n" +
                        "\n" +
                        "제1조(목적 등)\n" +
                        "\n" +
                        "① 본원 (꽈배기) 이용자 약관(이하 \"본 약관\"이라 합니다)은 이용자가 본원에서 제공하는 인터넷 관련 서비스(이하 \"서비스\"라 합니다)를 이용함에 있어 회원과 본원의 권리·의무 및 책임사항을 규정함을 목적으로 합니다.\n" +
                        "② 회원이 되고자 하는 자가 본원에서 정한 소정의 절차를 거쳐서 \"회원가입\" 단추를 누르면 본 약관에 동의하는 것으로 간주합니다. 본 약관에 정하는 이외의 회원과 본원의 권리, 의무 및 책임사항에 관해서는 전기통신사업법 기타 대한민국의 관련 법령과 상관습에 의합니다.\n" +
                        "\n" +
                        "제2조(회원의 정의)\n" +
                        "\n" +
                        "\"회원\"이란 본원에 접속하여 본 약관에 따라 본원 온라인 회원으로 가입하여 본원이 제공하는 서비스를 받는 자를 말합니다.\n" +
                        "\n" +
                        "제3조 (회원 가입)\n" +
                        "\n" +
                        "① 회원이 되고자 하는 자는 본원이 정한 가입 양식에 따라 회원정보를 기입하고 \"회원가입\" 단추를 누르는 방법으로 회원 가입을 신청합니다.\n" +
                        "② 본원은 제1항과 같이 회원으로 가입할 것을 신청한 자가 다음 각 호에 해당하지 않는 한 신청한 자를 회원으로 등록합니다.\n" +
                        "1. 가입신청자가 본 약관 제6조 제3항에 의하여 이전에 회원자격을 상실한 적이 있는 경우. 다만 제6조 제3항에 의한 회원자격 상실 후 3년이 경과한 자로서 본원의 회원 재가입 승낙을 얻은 경우에는 예외로 합니다.\n" +
                        "2. 등록 내용에 허위, 기재누락, 오기가 있는 경우\n" +
                        "3. 기타 회원으로 등록하는 것이 본원의 기술상 현저히 지장이 있다고 판단되는 경우\n" +
                        "③ 회원가입계약의 성립시기는 본원의 승낙이 가입신청자에게 도달한 시점으로 합니다.\n" +
                        "④ 회원은 제1항의 회원정보 기재 내용에 변경이 발생한 경우, 즉시 변경사항을 정정하여 기재하여야 합니다.\n" +
                        "\n" +
                        "제4조(서비스의 제공 및 변경)\n" +
                        "\n" +
                        "① 본원은 회원에게 아래와 같은 서비스를 제공합니다.\n" +
                        "1. 본원 뉴스 메일 서비스\n" +
                        "2. 본원 온라인 상담실 / 예약 이용 서비스\n" +
                        "3. 본원 온라인 회원을 위한 섹션 및 컨텐츠 서비스\n" +
                        "4. 기타 본원이 타 업체와 제휴해서 제공하는 각종 서비스\n" +
                        "② 본원은 그 변경될 서비스의 내용 및 제공일자를 제7조 제2항에서 정한 방법으로 회원에게 통지하고, 제1항에 정한 서비스를 변경하여 제공할 수 있습니다.\n" +
                        "\n" +
                        "제5조(서비스의 중단)\n" +
                        "\n" +
                        "① 본원은 컴퓨터 등 정보통신설비의 보수점검·교체 및 고장, 통신의 두절 등의 사유가 발생한 경우에는 서비스의 제공을 일시적으로 중단할 수 있고, 새로운 서비스로의 교체 기타 본원이 적절하다고 판단하는 사유에 기하여 현재 제공되는 서비스를 완전히 중단할 수 있습니다.\n" +
                        "② 제1항에 의한 서비스 중단의 경우에는 본원은 제7조 제2항에서 정한 방법으로 회원에게 통지합니다. 다만, 본원이 통제할 수 없는 사유로 인한 서비스의 중단(시스템 관리자의 고의, 과실이 없는 디스크 장애, 시스템 다운 등)으로 인하여 사전 통지가 불가능한 경우에는 그러하지 아니합니다.\n" +
                        "\n" +
                        "제6조(회원 탈퇴 및 자격 상실 등)\n" +
                        "\n" +
                        "① 회원은 본원에 언제든지 자신의 회원 등록을 말소해 줄 것(회원 탈퇴)을 요청할 수 있으며, 본원은 위 요청을 받은 즉시 해당 회원의 회원 등록 말소를 위한 절차를 밟습니다.\n" +
                        "② 회원이 다음 각 호의 사유에 해당하는 경우, 본원은 회원의 회원자격을 적절한 방법으로 제한 및 정지, 상실시킬 수 있습니다.\n" +
                        "1. 가입 신청 시에 허위 내용을 등록한 경우\n" +
                        "2. 다른 사람의 본원 사이트 이용을 방해하거나 그 정보를 도용하는 등 전자거래질서를 위협하는 경우\n" +
                        "3. 본원을 이용하여 법령과 본 약관이 금지하거나 공서양속에 반하는 행위를 하는 경우\n" +
                        "③ 본원이 회원의 회원자격을 상실시키기로 결정한 경우에는 회원등록을 말소합니다. 이 경우 회원인 회원에게 회원등록 말소 전에 이를 통지하고, 소명할 기회를 부여합니다.\n" +
                        "\n" +
                        "제7조(회원에 대한 통지)\n" +
                        "\n" +
                        "① 본원이 특정 회원에 대한 통지를 하는 경우 본원에 등록한 메일주소로 할 수 있습니다.\n" +
                        "② 본원이 불특정다수 회원에 대한 통지를 하는 경우 1주일이상 본원 게시판에 게시함으로써 개별 통지에 갈음할 수 있습니다.\n" +
                        "\n" +
                        "제8조(회원의 개인정보보호)\n" +
                        "\n" +
                        "본원은 관련법령이 정하는 바에 따라서 회원 등록정보를 포함한 회원의 개인정보를 보호하기 위하여 노력합니다. 회원의 개인정보보호에 관해서는 관련법령 및 본원이 정하는 \"개인정보취급방침\"에 정한 바에 의합니다.\n" +
                        "\n" +
                        "제9조(본원의 의무)\n" +
                        "\n" +
                        "① 본원은 법령과 본 약관이 금지하거나 공서양속에 반하는 행위를 하지 않으며 본 약관이 정하는 바에 따라 지속적이고, 안정적으로 서비스를 제공하기 위해서 노력합니다.\n" +
                        "② 본원은 회원이 안전하게 인터넷 서비스를 이용할 수 있도록 회원의 개인정보(신용정보포함)보호를 위한 보안 시스템을 구축합니다.\n" +
                        "③ 본원은 회원이 원하지 않는 영리목적의 광고성 전자우편을 발송하지 않습니다.\n" +
                        "\n" +
                        "제10조(회원의 ID 및 비밀번호에 대한 의무)\n" +
                        "\n" +
                        "① 본원이 관계법령, \"개인정보보호정책\"에 의해서 그 책임을 지는 경우를 제외하고, 자신의 ID와 비밀번호에 관한 관리책임은 각 회원에게 있습니다.\n" +
                        "② 회원은 자신의 ID 및 비밀번호를 제3자에게 이용하게 해서는 안됩니다.\n" +
                        "③ 회원은 자신의 ID 및 비밀번호를 도난당하거나 제3자가 사용하고 있음을 인지한 경우에는 바로 본원에 통보하고 본원의 안내가 있는 경우에는 그에 따라야 합니다.\n" +
                        "\n" +
                        "제11조(회원의 의무)\n" +
                        "\n" +
                        "① 회원은 다음 각 호의 행위를 하여서는 안됩니다.\n" +
                        "1. 회원가입신청 또는 변경 시 허위내용을 등록하는 행위\n" +
                        "2. 본원에 게시된 정보를 변경하는 행위\n" +
                        "3. 본원 기타 제3자의 인격권 또는 지적재산권을 침해하거나 업무를 방해하는 행위\n" +
                        "4. 다른 회원의 ID를 도용하는 행위\n" +
                        "5. 정크메일(junk mail), 스팸메일(spam mail), 행운의 편지(chain letters), 피라미드 조직에 가입할 것을 권유하는 메일, 외설 또는 폭력적인 메시지·화상·음성 등이 담긴 메일을 보내거나 기타 공서양속에 반하는 정보를 공개 또는 게시하는 행위.\n" +
                        "6. 관련 법령에 의하여 그 전송 또는 게시가 금지되는 정보(컴퓨터 프로그램 등)의 전송 또는 게시하는 행위\n" +
                        "7. 본원의 직원이나 본원 인터넷 서비스의 관리자를 가장하거나 사칭하여 또는 타인의 명의를 도용하여 글을 게시하거나 메일을 발송하는 행위\n" +
                        "8. 컴퓨터 소프트웨어, 하드웨어, 전기통신 장비의 정상적인 가동을 방해, 파괴할 목적으로 고안된 소프트웨어 바이러스, 기타 다른 컴퓨터 코드, 파일, 프로그램을 포함하고 있는 자료를 게시하거나 전자우편으로 발송하는 행위\n" +
                        "9. 스토킹(stalking) 등 다른 회원을 괴롭히는 행위\n" +
                        "10. 다른 회원에 대한 개인정보를 동의 없이 수집, 저장, 공개하는 행위\n" +
                        "11. 불특정 다수의 자를 대상으로 하여 광고 또는 선전을 게시하거나 스팸메일을 전송하는 등의 방법으로 본원의 서비스를 이용하여 영리목적의 활동을 하는 행위\n" +
                        "12. 본원이 제공하는 서비스에 정한 약관 기타 서비스 이용에 관한 규정을 위반하는 행위\n" +
                        "② 제1항에 해당하는 행위를 한 회원이 있을 경우 본원은 본 약관 제6조 제2, 3항에서 정한 바에 따라 회원의 회원자격을 적절한 방법으로 제한 및 정지, 상실시킬 수 있습니다.\n" +
                        "③ 회원은 그 귀책사유로 인하여 본원이나 다른 회원이 입은 손해를 배상할 책임이 있습니다.\n" +
                        "\n" +
                        "제 12조 (공개게시물의 삭제 및 비공개)\n" +
                        "\n" +
                        "회원의 공개게시물의 내용이 다음 각 호에 해당하는 경우 본원은 회원에게 사전 통지 없이 해당 공개게시물을 삭제할 수 있고, 해당 회원의 회원 자격을 제한, 정지 또는 상실시킬 수 있습니다.\n" +
                        "1. 다른 회원 또는 제3자를 비방하거나 중상 모략으로 명예를 손상시키는 내용\n" +
                        "2. 공서양속에 위반되는 내용의 정보, 문장, 도형 등을 유포하는 내용\n" +
                        "3. 범죄행위와 관련이 있다고 판단되는 내용\n" +
                        "4. 다른 회원 또는 제3자의 저작권 등 기타 권리를 침해하는 내용\n" +
                        "5. 광고성 또는 상업적 목적이 두드러진 경우\n" +
                        "6. 기타 관계 법령에 위배된다고 판단되는 내용\n" +
                        "\n" +
                        "제13조(저작권의 귀속 및 이용제한)\n" +
                        "\n" +
                        "① 본원이 작성한 저작물에 대한 저작권 기타 지적재산권은 본원에 귀속합니다.\n" +
                        "② 회원은 본원을 이용함으로써 얻은 정보를 본원의 사전승낙 없이 복제, 전송, 출판, 배포, 방송 기타 방법에 의하여 영리목적으로 이용하거나 제3자에게 이용하게 하여서는 안됩니다.\n" +
                        "\n" +
                        "제14조(상담에 관한 규정)\n" +
                        "\n" +
                        "① 서비스에서 진행된 상담의 내용은 개인 신상정보를 삭제한 다음 아래와 같은 목적으로 사용할 수 있습니다.\n" +
                        "1) 학술활동\n" +
                        "2) 진료활동\n" +
                        "3) 의료서비스\n" +
                        "4) 인쇄물, CD-ROM 등의 저작활동\n" +
                        "5) FAQ, 추천상담 등의 서비스 내용의 일부\n" +
                        "② 아래와 같은 상담을 신청하는 경우에는 상담 서비스를 전체 또는 일부 제공하지 않을 수 있습니다.\n" +
                        "1) 같은 내용의 상담을 반복하여 신청하는 경우\n" +
                        "2) 상식에 어긋나는 표현을 사용하거나 비속어를 사용하여 상담을 신청하는 경우\n" +
                        "3) 진단명을 요구하는 상담을 신청하는 경우\n" +
                        "4) 치료비, 검사비, 의약품 가격, 의약품의 효과 등에 대하여 상담을 신청하는 경우\n" +
                        "5) 타인을 해하기 위한 정보 취득목적으로 상담하는 경우\n" +
                        "\n" +
                        "제15조(약관의 개정)\n" +
                        "\n" +
                        "① 본원은 약관의 규제 등에 관한 법률, 전자거래기본법, 전자서명법, 정보통신망 이용촉진 등에 관한 법률 등 관련법을 위배하지 않는 범위에서 본 약관을 개정할 수 있습니다.\n" +
                        "② 본원이 본 약관을 개정할 경우에는 적용일자 및 개정사유를 명시하여 현행약관과 함께 초기화면에 그 적용일자 7일 이전부터 적용일자 전일까지 공지합니다.\n" +
                        "③ 본원이 본 약관을 개정할 경우에는 그 개정약관은 개정된 내용이 관계 법령에 위배되지 않는 한 개정 이전에 회원으로 가입한 회원에게도 적용됩니다.\n" +
                        "④ 변경된 약관에 이의가 있는 회원은 제6조 제1항에 따라 탈퇴할 수 있습니다.\n" +
                        "\n" +
                        "제16조(재판관할)\n" +
                        "\n" +
                        "본원과 회원간에 발생한 서비스 이용에 관한 분쟁으로 인한 소는 민사소송법상의 관할을 가지는 대한민국의 법원에 제기합니다.\n" +
                        "\n" +
                        "부 칙\n" +
                        "① 이 약관은 2021년 9월 28일을 시작으로 새로운 약관이 나오기 전까지 사용합니다.");
                builder.setNegativeButton("예",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(),"확인되었습니다.",Toast.LENGTH_LONG).show();
                                checkBox3.setChecked(true);
                            }
                        });

                builder.setPositiveButton("아니요",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(),"아니오를 선택하셨습니다",Toast.LENGTH_LONG).show();

                            }
                        });
                builder.show();
            }
        });
}


//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if (currentUser != null) {
//            Log.d(TAG, "onStart: " + currentUser + " " + currentUser.getProviderId() + " " + mAuth);
//            reload();
//        }
//    }

    private void updateUserInfo(String uid, String id, String pw, String name, String phone, String birth, boolean is_target) {
        User user = null;
        if (is_target) {
            user = new User(uid, id, pw, name, phone, birth, 1);
        } else {
            user = new User(uid, id, pw, name, phone, birth, 0);
        }
        user.setIs_surveyed(false);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mRef = database.getReference();
        mRef.child("Users").child(uid).setValue(user);
    }

    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        // [END start_phone_auth]
    }

    private PhoneAuthCredential verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]
        return credential;
    }


    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success");
                    Toast.makeText(RegisterActivity.this, "회원가입 성공!", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Locale.setDefault(Locale.KOREAN);
                    Toast.makeText(RegisterActivity.this, task.getException().getLocalizedMessage(),
                            Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });
    }

    private void linkAccount(AuthCredential credential) {
        mAuth.getCurrentUser().linkWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "linkWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();
                            updateUI(user);
                        } else {
                            Log.w(TAG, "linkWithCredential:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "SNS 연동 실패",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Log.d(TAG, "updateUI: " + (user.getUid().equals(mAuth.getUid())));
            updateUserInfo(mAuth.getUid(), email, password, name, phone, birthday, cb_target.isChecked());
            // 후원 대상 체크 시
            if (cb_target.isChecked()) {
                // setResult 및 Intent 수정 필요
                setResult(0);
                is_signuped = true;
                moveToTargetDetailsActivity();
            }
            // 일반 회원일 경우
            else {
                finish();
            }
        }
    }

    private void moveToTargetDetailsActivity() {
        Intent intent = new Intent(RegisterActivity.this, TargetDetailsActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("phone", phone);
        intent.putExtra("birth", birthday);
        intent.putExtra("Uid", mAuth.getUid());
        startActivityForResult(intent, TARGET);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 정보입력 다 했는지 확인
        Log.d(TAG, "onActivityResult: " + requestCode + " " + resultCode);
        if (requestCode == TARGET && resultCode == 200) {
            finish();
        }
    }

    private void init() {
        Locale.setDefault(Locale.KOREAN);
        mAuth = FirebaseAuth.getInstance();


        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_login);
                finish();
            }
        });
        cb_target = findViewById(R.id.cb_target_check);

        et_register_id = findViewById(R.id.et_register_id);
        et_register_pw = findViewById(R.id.et_register_pw);
        et_register_pw_check = findViewById(R.id.et_register_pw_check);
        et_register_name = findViewById(R.id.et_register_name);
        et_register_phone = findViewById(R.id.et_register_phone);
        et_register_no_check = findViewById(R.id.et_register_no_check);
        et_register_birth = findViewById(R.id.et_register_birth);

        btn_check_id = findViewById(R.id.btn_check_id);
        btn_check_phone = findViewById(R.id.btn_check_phone);
        btn_check_submit = findViewById(R.id.btn_check_submit);

        btn_register_save = findViewById(R.id.btn_register_save);
    }

    private void reload() {
        mAuth.signOut();
    }

}
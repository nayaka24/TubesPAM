package com.example.adimas_8.ims_project;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Form_Peminjam extends AppCompatActivity {
    private Button CreateAccountButton;
    private EditText InputGedung, InputRuang, InputMatkul, InputMulai, InputSelesai, InputTanggal;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pinjam);

        CreateAccountButton = (Button) findViewById(R.id.submit_pinjam);
        InputGedung = (EditText) findViewById(R.id.pinjam_gedung);
        InputRuang = (EditText) findViewById(R.id.pinjam_ruang);
        InputMatkul = (EditText) findViewById(R.id.pinjam_matkul);
        InputTanggal = (EditText) findViewById(R.id.pinjam_tanggal);
        InputMulai = (EditText) findViewById(R.id.pinjam_mulai);
        InputSelesai = (EditText) findViewById(R.id.pinjam_selesai);
        loadingBar = new ProgressDialog(this);



        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount();
            }
        });
    }

    private void CreateAccount() {
        String gedung = InputGedung.getText().toString();
        String ruang = InputRuang.getText().toString();
        String matkul = InputMatkul.getText().toString();
        String tanggal= InputTanggal.getText().toString();
        String mulai = InputMulai.getText().toString();
        String selesai = InputSelesai.getText().toString();

        if (TextUtils.isEmpty(gedung)) {
            Toast.makeText(this, "Masukkan Nama Ruang", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(ruang)) {
            Toast.makeText(this, "Masukkan Nama Ruang", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(matkul)) {
            Toast.makeText(this, "Masukkan Mata Kuliah", Toast.LENGTH_LONG).show();
        }else if (TextUtils.isEmpty(tanggal)) {
            Toast.makeText(this, "Masukkan Tanggal", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(mulai)) {
            Toast.makeText(this, "Masukkan Waktu Mulai Kelas", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(selesai)) {
            Toast.makeText(this, "Masukkan Waktu Selesai Kelas", Toast.LENGTH_LONG).show();

        }else if (TextUtils.equals(mulai,selesai)){
            Toast.makeText(this, "Waktu tidak boleh sama",Toast.LENGTH_LONG).show();

        } else {
            loadingBar.setTitle("Sedang Mengajukann Ruangan");
            loadingBar.setMessage("Harap tunggu");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            validatepinjam(gedung, ruang, tanggal, matkul, mulai, selesai);
        }
    }

    private void validatepinjam(final String gedung, final String ruang, final String tanggal, final String matkul, final String mulai, final String selesai) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child("Pinjam_Kelas").child(gedung).child(ruang).exists())) {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("gedung", gedung);
                    userdataMap.put("ruang", ruang);
                    userdataMap.put("matkul", matkul);
                    userdataMap.put("tanggal", tanggal);
                    userdataMap.put("mulai", mulai);
                    userdataMap.put("selesai", selesai);

                    RootRef.child("Pinjam_Kelas").child(gedung).child(ruang).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Form_Peminjam.this, "Terima Kasih, Ruang telah anda Ajukan", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                        Intent intent = new Intent(Form_Peminjam.this, Main_Menu.class);
                                        startActivity(intent);
                                    } else {
                                        loadingBar.dismiss();
                                        Toast.makeText(Form_Peminjam.this, "Maaf Ruang telah dipakai", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(Form_Peminjam.this, ruang+ "ini telah dipesan", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(Form_Peminjam.this, "Mohon Masukkan ruang yang lain", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Form_Peminjam.this, Main_Menu.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

package com.betproyecto.proyectogestortesteo.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.betproyecto.proyectogestortesteo.MainActivity;
import com.betproyecto.proyectogestortesteo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin, buttonRegister;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.Base_Theme_ProyectoGestorTesteo);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegister = findViewById(R.id.buttonRegis);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }
    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // Verifica si el correo electrónico y la contraseña son válidos y no están vacíos
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(LoginActivity.this, "Ingrese su correo electrónico", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "Ingrese su contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(LoginActivity.this, "Ingrese un correo electrónico válido", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(LoginActivity.this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Registro exitoso
                            Toast.makeText(LoginActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();

                            // Obtén el ID del usuario recién registrado
                            String userId = firebaseAuth.getCurrentUser().getUid();

                            // Crea un nodo "usuarios" utilizando el ID del usuario
                            DatabaseReference usuariosRef = FirebaseDatabase.getInstance().getReference().child("usuarios").child(userId);

                            // Crea el nodo "my_objects" dentro del nodo "usuarios" utilizando el ID del usuario
                            DatabaseReference myObjectsRef = usuariosRef.child("my_objects");

                            // Puedes agregar más lógica aquí, si lo deseas.

                            // Iniciar MainActivity después de registrar al usuario

                        } else {
                            // Error en el registro
                            Toast.makeText(LoginActivity.this, "Error al Registrarse - Tienes una Cuenta", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void loginUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // Verifica si el correo electrónico y la contraseña son válidos y no están vacíos
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(LoginActivity.this, "Ingrese su correo electrónico", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "Ingrese su contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(LoginActivity.this, "Ingrese un correo electrónico válido", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(LoginActivity.this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Inicio de sesión exitoso
                            Toast.makeText(LoginActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();

                            // Obtén el ID del usuario que ha iniciado sesión
                            String userId = firebaseAuth.getCurrentUser().getUid();

                            // Cambia la referencia de la base de datos para el usuario actual
                            DatabaseReference myObjectsRef = FirebaseDatabase.getInstance().getReference().child("usuarios").child(userId).child("my_objects");

                            // Guarda el estado de inicio de sesión del usuario
                            saveLoginStatus(true);

                            // Iniciar MainActivity después de iniciar sesión
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish(); // Finaliza LoginActivity para que el usuario no pueda volver a él
                        } else {
                            // Error en el inicio de sesión
                            Toast.makeText(LoginActivity.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    private void saveLoginStatus(boolean isLoggedIn) {
        SharedPreferences preferences = getSharedPreferences("login_status", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isLoggedIn", isLoggedIn);
        editor.apply();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Verifica si el usuario ya ha iniciado sesión previamente
        SharedPreferences preferences = getSharedPreferences("login_status", MODE_PRIVATE);
        boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);
        if (isLoggedIn) {
            // Si el usuario ya ha iniciado sesión previamente, dirígelo directamente a MainActivity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Finaliza LoginActivity para que el usuario no pueda volver a él
        }
    }










}
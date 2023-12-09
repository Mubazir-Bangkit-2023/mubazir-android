package com.foodwaste.mubazir.presentation.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.foodwaste.mubazir.R
import com.foodwaste.mubazir.presentation.common.component.TextError
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SignUpScreen(
    navController: NavHostController,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit){
        viewModel.snackbar.collectLatest { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    SignUpScreen(
        snackbarHostState = snackbarHostState,
        nameFieldState = viewModel.nameField,
        emailFieldState = viewModel.emailField,
        phoneFieldState = viewModel.phoneField,
        passwordFieldState = viewModel.passwordField,
        confirmPasswordFieldState = viewModel.confirmPasswordField,
        onNameChange = viewModel::onNameChange,
        onEmailChange = viewModel::onEmailChange,
        onPhoneChange = viewModel::onPhoneChange,
        onPasswordChange = viewModel::onPasswordChange,
        onConfirmPasswordChange = viewModel::onConfirmPasswordChange,
        fulfilledState = viewModel.fulfilled,
        loadingState = viewModel.loading,
        onClickButtonSignUp = viewModel::signUp,
        onNavigateToSignIn = {
            navController.navigate("sign_in")
        }
    )
}


@Composable
fun SignUpScreen(
    snackbarHostState: SnackbarHostState,
    nameFieldState: StateFlow<Pair<String, Int?>>,
    emailFieldState: StateFlow<Pair<String, Int?>>,
    phoneFieldState: StateFlow<Pair<String, Int?>>,
    passwordFieldState: StateFlow<Pair<String, Int?>>,
    confirmPasswordFieldState: StateFlow<Pair<String, Int?>>,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    fulfilledState: StateFlow<Boolean>,
    loadingState: StateFlow<Boolean>,
    onClickButtonSignUp: () -> Unit,
    onNavigateToSignIn: () -> Unit,
) {

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) {innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.login_asset),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.25f),
                contentScale = ContentScale.FillWidth
            )
            ElevatedCard(
                modifier = Modifier
                    .padding(15.dp, 10.dp)
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .align(Alignment.BottomCenter),
                shape = RoundedCornerShape(15.dp),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 5.dp
                )
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(20.dp)
                ) {

                    Text(
                        text = stringResource(id = R.string.text_signup),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                        modifier = Modifier
                            .fillMaxWidth()
                    )

                    Column(
                        verticalArrangement = Arrangement.spacedBy(7.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        val focusManager = LocalFocusManager.current

                        var isPasswordVisible by rememberSaveable {
                            mutableStateOf(false)
                        }

                        Column {
                            val nameField by nameFieldState.collectAsState()
                            val (name, nameError) = nameField

                            OutlinedTextField(
                                value = name,
                                onValueChange = onNameChange,
                                shape = RoundedCornerShape(30.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedBorderColor = MaterialTheme.colorScheme.primary
                                ),
                                label = {
                                    Text(text = stringResource(id = R.string.text_full_name))
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Filled.Badge,
                                        contentDescription = null
                                    )
                                },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Next
                                ),
                                isError = nameError != null,
                                modifier = Modifier
                                    .fillMaxWidth(),
                            )
                            Spacer(modifier = Modifier.height(3.dp))
                            TextError(
                                textRes = nameError,
                                modifier = Modifier.align(Alignment.End)
                            )
                        }

                        Column {
                            val emailField by emailFieldState.collectAsState()
                            val (email, emailError) = emailField

                            OutlinedTextField(
                                value = email,
                                onValueChange = onEmailChange,
                                shape = RoundedCornerShape(30.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedBorderColor = MaterialTheme.colorScheme.primary
                                ),
                                label = {
                                    Text(text = stringResource(id = R.string.text_email))
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Filled.AccountCircle,
                                        contentDescription = null
                                    )
                                },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Email,
                                    imeAction = ImeAction.Next
                                ),
                                isError = emailError != null,
                                modifier = Modifier
                                    .fillMaxWidth(),
                            )
                            Spacer(modifier = Modifier.height(3.dp))
                            TextError(
                                textRes = emailError,
                                modifier = Modifier.align(Alignment.End)
                            )
                        }

                        Column {
                            val phoneField by phoneFieldState.collectAsState()
                            val (phone, phoneError) = phoneField
                            OutlinedTextField(
                                value = phone,
                                onValueChange = onPhoneChange,
                                shape = RoundedCornerShape(30.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedBorderColor = MaterialTheme.colorScheme.primary
                                ),
                                label = {
                                    Text(text = stringResource(id = R.string.text_phone_number_or_whatsapp))
                                },
                                placeholder = {
                                    Text(text = stringResource(id = R.string.text_phone_number_format_example))
                                },
                                leadingIcon = {
                                    Text(
                                        text = stringResource(id = R.string.text_phone_code),
                                        fontWeight = FontWeight.Bold
                                    )
                                },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Phone,
                                    imeAction = ImeAction.Next
                                ),
                                isError = phoneError != null,
                                modifier = Modifier
                                    .fillMaxWidth(),
                            )
                            Spacer(modifier = Modifier.height(3.dp))
                            TextError(
                                textRes = phoneError,
                                modifier = Modifier.align(Alignment.End)
                            )
                        }

                        Column {
                            val passwordField by passwordFieldState.collectAsState()
                            val (password, passwordError) = passwordField

                            OutlinedTextField(
                                value = password,
                                onValueChange = onPasswordChange,
                                shape = RoundedCornerShape(30.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedBorderColor = MaterialTheme.colorScheme.primary
                                ),
                                label = {
                                    Text(text = stringResource(id = R.string.text_password))
                                },
                                singleLine = true,
                                leadingIcon = {
                                    Icon(imageVector = Icons.Filled.Lock, contentDescription = null)
                                },
                                trailingIcon = {
                                    IconButton(
                                        onClick = { isPasswordVisible = !isPasswordVisible },
                                        modifier = Modifier
                                            .padding(5.dp)
                                    ) {
                                        Icon(
                                            imageVector = if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                },
                                visualTransformation = if (!isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Next
                                ),
                                isError = passwordError != null,
                                modifier = Modifier
                                    .fillMaxWidth(),
                            )
                            Spacer(modifier = Modifier.height(3.dp))
                            TextError(
                                textRes = passwordError,
                                modifier = Modifier.align(Alignment.End)
                            )
                        }

                        Column {
                            val confirmPasswordField by confirmPasswordFieldState.collectAsState()
                            val (confirmPassword, confirmPasswordError) = confirmPasswordField

                            OutlinedTextField(
                                value = confirmPassword,
                                onValueChange = onConfirmPasswordChange,
                                shape = RoundedCornerShape(30.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedBorderColor = MaterialTheme.colorScheme.primary
                                ),
                                label = {
                                    Text(text = stringResource(id = R.string.text_confirm_password))
                                },
                                singleLine = true,
                                leadingIcon = {
                                    Icon(imageVector = Icons.Filled.Lock, contentDescription = null)
                                },
                                trailingIcon = {
                                    IconButton(
                                        onClick = { isPasswordVisible = !isPasswordVisible },
                                        modifier = Modifier
                                            .padding(5.dp)
                                    ) {
                                        Icon(
                                            imageVector = if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                },
                                visualTransformation = if (!isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Done
                                ),
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        focusManager.clearFocus()
                                    }
                                ),
                                isError = confirmPasswordError != null,
                                modifier = Modifier
                                    .fillMaxWidth(),
                            )
                            Spacer(modifier = Modifier.height(3.dp))
                            TextError(
                                textRes = confirmPasswordError,
                                modifier = Modifier.align(Alignment.End)
                            )
                        }
                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        val isFulfilled by fulfilledState.collectAsState()
                        val isLoading by loadingState.collectAsState()
                        Button(
                            onClick = onClickButtonSignUp,
                            enabled = isFulfilled && !isLoading,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            shape = RoundedCornerShape(30.dp)
                        ) {
                            if (isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .align(Alignment.CenterVertically),
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            } else {
                                Text(
                                    text = stringResource(id = R.string.text_signup).uppercase(),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = MaterialTheme.typography.bodyLarge.fontSize
                                )
                            }
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = stringResource(id = R.string.text_already_have_an_account),
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onBackground,
                                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                            )
                            TextButton(onClick = onNavigateToSignIn) {
                                Text(
                                    text = stringResource(id = R.string.text_signin),
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                                    textDecoration = TextDecoration.Underline,
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}


@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen(
        navController = rememberNavController()
    )
}
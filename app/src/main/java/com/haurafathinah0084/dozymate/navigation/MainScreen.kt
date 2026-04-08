package com.haurafathinah0084.dozymate.navigation

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.haurafathinah0084.dozymate.R
import com.haurafathinah0084.dozymate.ui.theme.DozyMateTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.app_name))
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screen.Tips.route)
                    }) {
                        Icon(Icons.Outlined.Info, null)
                    }
                }
            )
        }
    ) { innerPadding ->
        ScreenContent(Modifier.padding(innerPadding))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenContent(modifier: Modifier = Modifier) {

    var mulai by rememberSaveable { mutableStateOf("") }
    var bangun by rememberSaveable { mutableStateOf("") }

    var mulaiError by rememberSaveable { mutableStateOf(false) }
    var bangunError by rememberSaveable { mutableStateOf(false) }

    var showStartPicker by remember { mutableStateOf(false) }
    var showEndPicker by remember { mutableStateOf(false) }

    val radioOptions = listOf(
        stringResource(R.string.anak),
        stringResource(R.string.remaja),
        stringResource(R.string.dewasa)
    )

    var usia by rememberSaveable { mutableStateOf(radioOptions[0]) }

    var durasi by rememberSaveable { mutableStateOf(0f) }
    var kategori by rememberSaveable { mutableStateOf("") }
    var tips by rememberSaveable { mutableStateOf("") }

    val kurang = stringResource(R.string.kurang)
    val cukup = stringResource(R.string.cukup)
    val berlebih = stringResource(R.string.berlebih)

    val tipsAnakKurang = stringResource(R.string.tips_anak_kurang)
    val tipsAnakCukup = stringResource(R.string.tips_anak_cukup)
    val tipsAnakLebih = stringResource(R.string.tips_anak_lebih)

    val tipsRemajaKurang = stringResource(R.string.tips_remaja_kurang)
    val tipsRemajaCukup = stringResource(R.string.tips_remaja_cukup)
    val tipsRemajaLebih = stringResource(R.string.tips_remaja_lebih)

    val tipsDewasaKurang = stringResource(R.string.tips_dewasa_kurang)
    val tipsDewasaCukup = stringResource(R.string.tips_dewasa_cukup)
    val tipsDewasaLebih = stringResource(R.string.tips_dewasa_lebih)

    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = stringResource(R.string.sleep_intro),
            style = MaterialTheme.typography.bodyLarge
        )

        OutlinedTextField(
            value = mulai,
            onValueChange = {},
            readOnly = true,
            isError = mulaiError,
            label = { Text(stringResource(R.string.mulai_tidur)) },
            supportingText = {
                if (mulaiError) {
                    Text(stringResource(R.string.input_invalid))
                }
            },
            trailingIcon = {
                IconButton(onClick = { showStartPicker = true }) {
                    Icon(Icons.Default.AccessTime, null)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = bangun,
            onValueChange = {},
            readOnly = true,
            isError = bangunError,
            label = { Text(stringResource(R.string.bangun_tidur)) },
            supportingText = {
                if (bangunError) {
                    Text(stringResource(R.string.input_invalid))
                }
            },
            trailingIcon = {
                IconButton(onClick = { showEndPicker = true }) {
                    Icon(Icons.Default.AccessTime, null)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            stringResource(R.string.kategori_usia),
            style = MaterialTheme.typography.titleMedium
        )

        Row(
            modifier = Modifier
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
        ) {

            radioOptions.forEach { text ->

                Row(
                    modifier = Modifier
                        .selectable(
                            selected = usia == text,
                            onClick = { usia = text },
                            role = Role.RadioButton
                        )
                        .weight(1f)
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    RadioButton(
                        selected = usia == text,
                        onClick = null
                    )

                    Text(
                        text = text,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
        }

        Text(
            stringResource(R.string.keterangan_usia),
            style = MaterialTheme.typography.bodySmall
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Button(
                onClick = {

                    mulaiError = mulai.isEmpty()
                    bangunError = bangun.isEmpty()

                    if (mulaiError || bangunError) return@Button

                    val startHour = mulai.substringBefore(":").toInt()
                    val startMinute = mulai.substringAfter(":").toInt()

                    val endHour = bangun.substringBefore(":").toInt()
                    val endMinute = bangun.substringAfter(":").toInt()

                    val startTotal = startHour * 60 + startMinute
                    val endTotal = endHour * 60 + endMinute

                    val diffMinute =
                        if (endTotal >= startTotal)
                            endTotal - startTotal
                        else
                            (24 * 60 - startTotal) + endTotal

                    durasi = diffMinute / 60f

                    if (usia == radioOptions[0]) {
                        when {
                            durasi < 9 -> {
                                kategori = kurang
                                tips = tipsAnakKurang
                            }
                            durasi <= 11 -> {
                                kategori = cukup
                                tips = tipsAnakCukup
                            }
                            else -> {
                                kategori = berlebih
                                tips = tipsAnakLebih
                            }
                        }
                    }

                    else if (usia == radioOptions[1]) {
                        when {
                            durasi < 8 -> {
                                kategori = kurang
                                tips = tipsRemajaKurang
                            }
                            durasi <= 10 -> {
                                kategori = cukup
                                tips = tipsRemajaCukup
                            }
                            else -> {
                                kategori = berlebih
                                tips = tipsRemajaLebih
                            }
                        }
                    }

                    else {
                        when {
                            durasi < 7 -> {
                                kategori = kurang
                                tips = tipsDewasaKurang
                            }
                            durasi <= 9 -> {
                                kategori = cukup
                                tips = tipsDewasaCukup
                            }
                            else -> {
                                kategori = berlebih
                                tips = tipsDewasaLebih
                            }
                        }
                    }

                },
                modifier = Modifier.weight(1f)
            ) {
                Text(stringResource(R.string.hitung))
            }

            OutlinedButton(
                onClick = {
                    mulai = ""
                    bangun = ""
                    usia = radioOptions[0]
                    durasi = 0f
                    kategori = ""
                    tips = ""
                    mulaiError = false
                    bangunError = false
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(stringResource(R.string.reset))
            }
        }

        if (durasi != 0f) {

            HorizontalDivider()

            Text(
                "${stringResource(R.string.durasi)} %.2f jam".format(durasi),
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                "${stringResource(R.string.kategori)} $kategori",
                style = MaterialTheme.typography.titleMedium
            )

            Text(tips)

            Button(
                onClick = {
                    shareData(
                        context,
                        "%.2f jam\n$kategori\n$tips".format(durasi)
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.bagikan))
            }
        }
    }

    if (showStartPicker) {
        TimePickerDialog(
            onDismiss = { showStartPicker = false },
            onConfirm = { h, m ->
                mulai = "%02d:%02d".format(h, m)
                showStartPicker = false
                mulaiError = false
            }
        )
    }

    if (showEndPicker) {
        TimePickerDialog(
            onDismiss = { showEndPicker = false },
            onConfirm = { h, m ->
                bangun = "%02d:%02d".format(h, m)
                showEndPicker = false
                bangunError = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    onDismiss: () -> Unit,
    onConfirm: (Int, Int) -> Unit
) {

    val state = rememberTimePickerState()

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(state.hour, state.minute)
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        text = {
            TimePicker(state = state)
        }
    )
}

private fun shareData(context: Context, message: String) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_TEXT, message)
    context.startActivity(Intent.createChooser(intent, "Share"))
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MainPreview() {
    DozyMateTheme {
        MainScreen(rememberNavController())
    }
}
package com.portfolio.app.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.portfolio.app.data.InMemoryProfileRepository
import com.portfolio.app.model.Profile
import com.portfolio.app.ui.theme.PortfolioTheme

/**
 * Entry-point screen: a single scrollable profile page composed of
 * self-contained, independently previewable sections.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(profile: Profile) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Portfolio") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        LazyColumn(
            contentPadding = PaddingValues(
                start = 20.dp,
                end = 20.dp,
                top = innerPadding.calculateTopPadding() + 8.dp,
                bottom = 32.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            item { ProfileHeaderSection(profile) }
            item { IdentitySection(profile) }
            item { ContactSection(profile) }
            item { AddressSection(profile) }
            item { HobbiesSection(profile.hobbies) }
        }
    }
}

@Composable
private fun ProfileHeaderSection(profile: Profile) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, bottom = 4.dp)
    ) {
        ProfileAvatar(photoUrl = profile.photoUrl, fullName = profile.fullName)
        Text(
            text = profile.fullName,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = profile.title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 4.dp)
        )
        Text(
            text = profile.bio,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 12.dp)
        )
    }
}

@Composable
private fun IdentitySection(profile: Profile) {
    InfoSection(title = "Identity") {
        DetailRow(label = "ID", value = profile.id)
    }
}

@Composable
private fun ContactSection(profile: Profile) {
    InfoSection(title = "Contact") {
        DetailRow(label = "Email", value = profile.email)
        DetailRow(label = "Phone", value = profile.phone)
    }
}

@Composable
private fun AddressSection(profile: Profile) {
    InfoSection(title = "Address") {
        Text(
            text = profile.address,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun HobbiesSection(hobbies: List<String>) {
    InfoSection(title = "Hobbies & Interests") {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            hobbies.forEach { hobby -> HobbyChip(text = hobby) }
        }
    }
}

@Preview(showBackground = true, name = "Profile — Light")
@Composable
private fun ProfileScreenLightPreview() {
    PortfolioTheme(darkTheme = false) {
        ProfileScreen(profile = InMemoryProfileRepository().getProfile())
    }
}

@Preview(showBackground = true, name = "Profile — Dark")
@Composable
private fun ProfileScreenDarkPreview() {
    PortfolioTheme(darkTheme = true) {
        ProfileScreen(profile = InMemoryProfileRepository().getProfile())
    }
}

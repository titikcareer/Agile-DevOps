package com.portfolio.app.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.portfolio.app.util.TextUtils

/**
 * Circular profile photo with a lettered-initials fallback while the
 * image loads or if it fails to load — the UI never shows a broken
 * image icon.
 */
@Composable
fun ProfileAvatar(
    photoUrl: String,
    fullName: String,
    modifier: Modifier = Modifier,
    size: androidx.compose.ui.unit.Dp = 96.dp
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape),
        contentAlignment = Alignment.Center
    ) {
        SubcomposeAsyncImage(
            model = photoUrl,
            contentDescription = "Profile photo of $fullName",
            loading = { InitialsFallback(fullName, size) },
            error = { InitialsFallback(fullName, size) }
        )
    }
}

@Composable
private fun InitialsFallback(fullName: String, size: androidx.compose.ui.unit.Dp) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = TextUtils.initialsOf(fullName),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

/** Small uppercase section label, used above each grouped block of info. */
@Composable
fun SectionLabel(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text.uppercase(),
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.outline,
        modifier = modifier
    )
}

/** A single label/value row used for contact and identity details. */
@Composable
fun DetailRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.padding(vertical = 8.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier.padding(end = 12.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.End,
            modifier = Modifier
        )
    }
}

/** A minimal, text-only chip for a single hobby/interest — no icon clutter. */
@Composable
fun HobbyChip(text: String) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        border = BorderStroke(0.dp, MaterialTheme.colorScheme.outline),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
        )
    }
}

/** Reusable outlined card wrapper that groups a titled section of content. */
@Composable
fun InfoSection(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScopeContent
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            SectionLabel(text = title, modifier = Modifier.padding(bottom = 12.dp))
            content()
        }
    }
}

/** Alias to keep the InfoSection signature concise and self-documenting. */
typealias ColumnScopeContent = @Composable () -> Unit

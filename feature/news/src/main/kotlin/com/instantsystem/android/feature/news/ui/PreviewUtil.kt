package com.instantsystem.android.feature.news.ui

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.core.Transition

class FakeAnimatedVisibilityScope(
    override val transition: Transition<EnterExitState>
) : AnimatedVisibilityScope
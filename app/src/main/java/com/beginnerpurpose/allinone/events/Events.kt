package com.beginnerpurpose.allinone.events

import androidx.lifecycle.Observer

class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}

class EventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(value: Event<T>) {
        value.getContentIfNotHandled()?.let { value ->
            onEventUnhandledContent(value)
        }
    }
}

class VoidEvent {
    private var hasBeenHandled = false

    fun hasBeenHandled(): Boolean = if (hasBeenHandled) {
        true
    } else {
        hasBeenHandled = true
        false
    }
}

class VoidEventObserver(private val onEventUnhandledContent: () -> Unit) : Observer<VoidEvent> {
    override fun onChanged(value: VoidEvent) {
        if (!value.hasBeenHandled()) {
            onEventUnhandledContent()
        }
    }
}
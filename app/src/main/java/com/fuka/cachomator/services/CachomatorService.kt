package com.fuka.cachomator.services

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

class CachomatorService : AccessibilityService() {
    override fun onServiceConnected() {
        Log.e(TAG, "Service started.")
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {

        if (AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED == event
                .eventType
        ) {
            lateinit var list: List<AccessibilityNodeInfo>
            val nodeInfo = event.source ?: return

            // search first for "Storage & cache"
            list = nodeInfo
                .findAccessibilityNodeInfosByText("Storage & cache")
            for (node in list) {
                // the clickable element is contained in a frame layout,
                // so we have to go parent 2 times
                val relativeLayout = node.parent
                val frameLayout = relativeLayout.parent
                frameLayout.performAction(AccessibilityNodeInfo.ACTION_CLICK)
            }

            // search for "Clear cache"
            list = nodeInfo
                .findAccessibilityNodeInfosByText("Clear cache")
            for (node in list) {
                node.performAction(AccessibilityNodeInfo.ACTION_CLICK)

            }
        }
    }

    override fun onInterrupt() {
        TODO("Not yet implemented")
    }

    companion object {
        private val TAG = CachomatorService::class.java
            .simpleName
    }
}

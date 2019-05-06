/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.android.gms.samples.vision.ocrreader;

import android.util.Log;
import android.util.SparseArray;

import com.google.android.gms.samples.vision.ocrreader.ui.camera.GraphicOverlay;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * A very simple Processor which gets detected TextBlocks and adds them to the overlay
 * as OcrGraphics.
 * TODO: Make this implement Detector.Processor<TextBlock> and add text to the GraphicOverlay
 */
public class OcrDetectorProcessor implements Detector.Processor<TextBlock>{

    private GraphicOverlay<OcrGraphic> graphicOverlay;
    private SortedMap<Integer,TextBlock> drugs = new TreeMap<>();
    public String drugName;
    public WebScrapper w;

    public OnScrappingDelegate onScrappingDelegate;

    public interface OnScrappingDelegate{
        void finished(Medicine medicine);
        void failed();
    }

    OcrDetectorProcessor(GraphicOverlay<OcrGraphic> ocrGraphicOverlay, OnScrappingDelegate onScrappingDelegate) {
        graphicOverlay = ocrGraphicOverlay;
        this.onScrappingDelegate = onScrappingDelegate;
    }

    // TODO:  Once this implements Detector.Processor<TextBlock>, implement the abstract methods.

    @Override
    public void receiveDetections(Detector.Detections<TextBlock> detections) {
        graphicOverlay.clear();
        SparseArray<TextBlock> items = detections.getDetectedItems();
        SortedMap<Integer,TextBlock> drugs = new TreeMap<>();
        for (int i = 0; i < items.size(); ++i) {
            TextBlock item = items.valueAt(i);
            if (item != null && item.getValue() != null) {
                drugs.put(item.getBoundingBox().height(),item);
                Log.d("Processor", "Text detected! " + item.getValue());
//                OcrGraphic graphic = new OcrGraphic(graphicOverlay, drugs.get(drugs.lastKey()));
//                graphicOverlay.add(graphic);
            }
        }
        OcrGraphic graphic = new OcrGraphic(graphicOverlay, drugs.get(drugs.lastKey()));
        graphicOverlay.add(graphic);
        Log.d("DrugName", "DrugName: "+ drugs.get(drugs.lastKey()).getValue());
        this.drugName = drugs.get(drugs.lastKey()).getValue();
        this.w = new WebScrapper(this.drugName);
        if (!w.getMedicine().Generic.isEmpty()){
            this.onScrappingDelegate.finished(this.w.getMedicine());
        }
        else {
            this.onScrappingDelegate.failed();
        }
    }

    @Override
    public void release() {
        graphicOverlay.clear();
    }
}

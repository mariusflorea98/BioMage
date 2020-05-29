/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biomage.algorithm;

import biomage.algorithm.template.BlurTemplate;
import biomage.algorithm.template.FloodFillTemplate;
import biomage.algorithm.template.HistogramTemplate;
import biomage.algorithm.template.InvertTemplate;
import biomage.algorithm.template.SharpenTemplate;
import biomage.algorithm.template.SobelTemplate;
import biomage.algorithm.template.iFilterTemplate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marius
 */
public class PipelineFactory {

    private List<iFilterTemplate> filterTemps;
    private final String pipe;

    public PipelineFactory(String pipe) {

        this.pipe = pipe;

    }

    public List<iFilterTemplate> getPipeline() {
        filterTemps = new ArrayList<>();
        switch (pipe) {
            case "BW_StemCells":
                filterTemps.add(new HistogramTemplate());
                filterTemps.add(new SharpenTemplate());
                filterTemps.add(new FloodFillTemplate());

                break;

            case "BW_SynteticCells":
                filterTemps.add(new HistogramTemplate());
                filterTemps.add(new InvertTemplate());
                filterTemps.add(new SobelTemplate());
                filterTemps.add(new FloodFillTemplate());
                break;

            case "RGB_Adipocytes":
                filterTemps.add(new HistogramTemplate());
                filterTemps.add(new BlurTemplate());
                filterTemps.add(new FloodFillTemplate());
                break;

            case "RGB_PreAdipocytes":

                filterTemps.add(new SharpenTemplate());
                filterTemps.add(new BlurTemplate());
                filterTemps.add(new FloodFillTemplate());
                break;
        }

        return filterTemps;
    }

}

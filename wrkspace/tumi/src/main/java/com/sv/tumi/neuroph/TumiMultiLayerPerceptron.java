package com.sv.tumi.neuroph;

import java.io.File;
import java.util.Arrays;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;

public class TumiMultiLayerPerceptron {

    public static void main(String[] args) {
        //new TumiMultiLayerPerceptron().run();
    	//new TumiMultiLayerPerceptron.getPrediction(0.500, 0.909, 1.000);
    }
    
    /**
     * Runs this sample
     */
    public void run() {
    	
    	
    	
       /* // create training set
        DataSet trainingSet = new DataSet(3, 3);
        trainingSet.addRow(new DataSetRow(new double[]{0.500, 0.091, 0.000}, new double[]{1,0,0}));
        trainingSet.addRow(new DataSetRow(new double[]{0.500, 0.091, 0.500}, new double[]{0,1,0}));
        trainingSet.addRow(new DataSetRow(new double[]{0.500, 0.091, 1.000}, new double[]{0,0,1}));
        trainingSet.addRow(new DataSetRow(new double[]{0.500, 0.364, 0.000}, new double[]{1,0,0}));
        trainingSet.addRow(new DataSetRow(new double[]{0.500, 0.364, 0.500}, new double[]{0,1,0}));
        trainingSet.addRow(new DataSetRow(new double[]{0.500, 0.364, 1.000}, new double[]{0,1,0}));
        
        // load saved neural network
        NeuralNetwork loadedMlPerceptron = NeuralNetwork.createFromFile("TumiCursoNivel.nnet");

        // test loaded neural network
        System.out.println("Testing loaded neural network");
        testNeuralNetwork(loadedMlPerceptron, trainingSet);*/
    }

    public static void testNeuralNetwork(NeuralNetwork neuralNet, DataSet testSet) {

        for(DataSetRow testSetRow : testSet.getRows()) {
            neuralNet.setInput(testSetRow.getInput());
            neuralNet.calculate();
            double[] networkOutput = neuralNet.getOutput();

            System.out.print("Input: " + Arrays.toString( testSetRow.getInput() ) );
            System.out.println(" Output: " + Arrays.toString( networkOutput) );
        }
    }   
    
	public double[] getPrediction(double position, double subTheme,
			double level, double nivelDesempeño, double nivelPersonal) {
		
		double[] inputs = new double[] { nivelDesempeño, nivelPersonal, position, subTheme, level };
		System.out.println("Nivel Desempeño " + nivelDesempeño
				+ " Nivel Personal " + nivelPersonal + " Cargo " + position
				+ " sub tema " + subTheme + " nivel tema " + level);

		ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("Tumi.nnet").getFile());
        
        // load saved neural network
        NeuralNetwork loadedMlPerceptron = NeuralNetwork.createFromFile(file);

		loadedMlPerceptron.setInput(inputs);
		loadedMlPerceptron.calculate();
		double[] networkOutput = loadedMlPerceptron.getOutput();

		System.out.print("Input: " + Arrays.toString(inputs));
		System.out.println(" Output: " + Arrays.toString(networkOutput));
		return networkOutput;

	}

}
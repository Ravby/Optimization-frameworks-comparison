/* Copyright 2009-2023 David Hadka
 *
 * This file is part of the MOEA Framework.
 *
 * The MOEA Framework is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * The MOEA Framework is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with the MOEA Framework.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.moeaframework.core;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.moeaframework.util.TypedProperties;
import org.apache.commons.text.StringTokenizer;
import org.moeaframework.core.NondominatedPopulation.DuplicateMode;
import org.moeaframework.core.indicator.Hypervolume;

/**
 * Global settings used by this framework.  The {@code PROPERTIES} object
 * contains the system properties and optionally the contents of a 
 * configuration file (properties in the configuration file take precedence).
 * By default, the {@code moeaframework.properties} file is loaded, but can be
 * specified using the {@code org.moeaframework.configuration} system
 * property.
 */
public class Settings {

	/**
	 * Level of significance or machine precision.
	 */
	public static final double EPS = 1e-10;
	
	/**
	 * The default buffer size.  Currently set to 4096 bytes.
	 */
	public static final int BUFFER_SIZE = 0x1000;
	
	/**
	 * The default population size.
	 */
	public static final int DEFAULT_POPULATION_SIZE = 100;
	
	/**
	 * Store the new line character to prevent repetitive calls to {@code System.getProperty("line.separator")}.
	 */
	public static final String NEW_LINE = System.getProperty("line.separator");

	/**
	 * The global properties object.
	 */
	public static final TypedProperties PROPERTIES;
	
	/**
	 * The prefix for all property keys.
	 */
	static final String KEY_PREFIX = createKey("org", "moeaframework");
	
	/**
	 * The property key for how to handle duplicate solutions in a nondominated population.
	 */
	static final String KEY_DUPLICATE_MODE = createKey(KEY_PREFIX, "core", "duplicate_mode");
	
	/**
	 * The property key for the power used in the generational distance calculation.
	 */
	static final String KEY_GD_POWER = createKey(KEY_PREFIX, "core", "indicator", "gd_power");
	
	/**
	 * The property key for the power used in the inverted generational distance calculation.
	 */
	static final String KEY_IGD_POWER = createKey(KEY_PREFIX, "core", "indicator", "igd_power");
	
	/**
	 * The property key to indicate that fast non-dominated sorting should be used.
	 */
	static final String KEY_FAST_NONDOMINATED_SORTING = createKey(KEY_PREFIX, "core", "fast_nondominated_sorting");
	
	/**
	 * The property key for the continuity correction flag.
	 */
	static final String KEY_CONTINUITY_CORRECTION = createKey(KEY_PREFIX, "util", "statistics", "continuity_correction");
	
	/**
	 * The property key for the hypervolume delta when determining the reference point.
	 */
	static final String KEY_HYPERVOLUME_DELTA = createKey(KEY_PREFIX, "core", "indicator", "hypervolume_delta");
	
	/**
	 * The prefix for specifying custom ideal points for different problems.
	 */
	static final String KEY_IDEALPT_PREFIX = createKey(KEY_PREFIX, "core", "indicator", "hypervolume_idealpt");
	
	/**
	 * The prefix for specifying custom reference points for different problems.
	 */
	static final String KEY_REFPT_PREFIX = createKey(KEY_PREFIX, "core", "indicator", "hypervolume_refpt");
	
	/**
	 * The property key for the hypervolume command.
	 */
	static final String KEY_HYPERVOLUME = createKey(KEY_PREFIX, "core", "indicator", "hypervolume");
	
	/**
	 * The property key for the hypervolume inversion flag.
	 */
	static final String KEY_HYPERVOLUME_INVERTED = createKey(KEY_PREFIX, "core", "indicator", "hypervolume_inverted");
	
	/**
	 * The property key for the hypervolume flag.
	 */
	static final String KEY_HYPERVOLUME_ENABLED = createKey(KEY_PREFIX, "core", "indicator", "hypervolume_enabled");
	
	/**
	 * The prefix for all problem property keys.
	 */
	static final String KEY_PROBLEM_PREFIX = createKey(KEY_PREFIX, "problem");
	
	/**
	 * The property key for the list of available problems.
	 */
	static final String KEY_PROBLEM_LIST = createKey(KEY_PROBLEM_PREFIX, "problems");
	
	/**
	 * The prefix for all PISA property keys.
	 */
	static final String KEY_PISA_PREFIX = createKey(KEY_PREFIX, "algorithm", "pisa");
	
	/**
	 * The property key for the list of available PISA algorithms.
	 */
	static final String KEY_PISA_ALGORITHMS = createKey(KEY_PISA_PREFIX, "algorithms");
	
	/**
	 * The property key for the poll rate.
	 */
	static final String KEY_PISA_POLL = createKey(KEY_PISA_PREFIX, "poll");
	
	/**
	 * The property key for the algorithms available in the diagnostic tool.
	 */
	static final String KEY_DIAGNOSTIC_TOOL_ALGORITHMS = createKey(KEY_PREFIX, "analysis", "diagnostics", "algorithms");
	
	/**
	 * The property key for the problems available in the diagnostic tool.
	 */
	static final String KEY_DIAGNOSTIC_TOOL_PROBLEMS = createKey(KEY_PREFIX, "analysis", "diagnostics", "problems");
	
	/**
	 * The property key for the genetic programming protected functions flag.
	 */
	static final String KEY_GP_PROTECTED_FUNCTIONS = createKey(KEY_PREFIX, "util", "tree", "protected_functions");
	
	/**
	 * The property key for the cleanup strategy when restarting from previous runs.
	 */
	static final String KEY_CLEANUP_STRATEGY = createKey(KEY_PREFIX, "analysis", "sensitivity", "cleanup");
	
	/**
	 * The property key for enabling debugging info when running external problems.
	 */
	static final String KEY_EXTERNAL_PROBLEM_DEBUGGING = createKey(KEY_PREFIX, "problem", "external_problem_debugging");
	
	/**
	 * Loads the properties.
	 */
	static {
		String resource = "moeaframework.properties";
		Properties properties = null;
		
		//attempt to access system properties
		try {
			properties = new Properties(System.getProperties());
			String configurationKey = createKey(KEY_PREFIX, "configuration");
			
			if (properties.containsKey(configurationKey)) {
				resource = properties.getProperty(configurationKey);
			}
		} catch (SecurityException e) {
			properties = new Properties();
		}
		
		//attempt to access properties file
		try {
			File file = new File(resource);
			
			if (file.exists()) {
				try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
					properties.load(reader);
				}
			} else {
				try (InputStream stream = Settings.class.getResourceAsStream("/" + resource)) {
					if (stream != null) {
						try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
							properties.load(reader);
						}
					}
					
				}
			}
		} catch (IOException e) {
			throw new FrameworkException(e);
		}
		
		PROPERTIES = new TypedProperties(properties);
	}
	
	/**
	 * Private constructor to prevent instantiation.
	 */
	private Settings() {
		super();
	}
	
	/**
	 * Returns {@code true} if continuity correction is enabled; {@code false}
	 * otherwise.  Rank-based statistical inference methods, such as the 
	 * Mann-Whitney U test and the Wilcoxon Signed-Ranks test, approximate the 
	 * test's discrete distribution with a continuous distribution for 
	 * computing the p-value. It has been recommended but not often employed in
	 * practice to apply a continuity correction.
	 * 
	 * @return {@code true} if continuity correction is enabled; {@code false}
	 *         otherwise
	 */
	public static boolean isContinuityCorrection() {
		return PROPERTIES.getBoolean(KEY_CONTINUITY_CORRECTION, false);
	}
	
	/**
	 * Returns the strategy used for handling duplicate solutions in a
	 * nondominated population.
	 * 
	 * @return the strategy for handling duplicate solutions
	 */
	public static DuplicateMode getDuplicateMode() {
		return DuplicateMode.valueOf(PROPERTIES.getString(KEY_DUPLICATE_MODE,
				DuplicateMode.NO_DUPLICATE_OBJECTIVES.name()).toUpperCase());
	}
	
	/**
	 * Returns the power used in the generational distance calculation.
	 * The default value is 2.0.
	 * 
	 * @return the power used in the generational distance calculation
	 */
	public static double getGDPower() {
		return PROPERTIES.getDouble(KEY_GD_POWER, 2.0);
	}
	
	/**
	 * Returns the power used in the inverted generational distance calculation.
	 * The default value is 1.0.
	 * 
	 * @return the power used in the inverted generational distance calculation
	 */
	public static double getIGDPower() {
		return PROPERTIES.getDouble(KEY_IGD_POWER, 1.0);
	}
	
	/**
	 * Returns the ideal point for the given problem, or {@code null} if
	 * one is not specified.
	 * 
	 * @param problem the problem name
	 * @return the ideal point
	 */
	public static double[] getIdealPoint(String problem) {
		return PROPERTIES.getDoubleArray(createKey(KEY_IDEALPT_PREFIX, problem), null);
	}
	
	/**
	 * Returns the reference point for the given problem, or {@code null} if
	 * one is not specified.
	 * 
	 * @param problem the problem name
	 * @return the reference point
	 */
	public static double[] getReferencePoint(String problem) {
		return PROPERTIES.getDoubleArray(createKey(KEY_REFPT_PREFIX, problem), null);
	}
	
	/**
	 * Returns {@code true} if fast non-dominated sorting should be used;
	 * or {@code false} if the naive non-dominated sorting implementation is
	 * preferred.  The default is {@code false} since while the fast version
	 * has better worst-case time complexity, the naive version tends to run
	 * faster except for a small number of edge cases.
	 * 
	 * @return {@code true} if fast non-dominated sorting should be used;
	 *         or {@code false} if the naive non-dominated sorting
	 *         implementation is preferred
	 */
	public static boolean useFastNondominatedSorting() {
		return PROPERTIES.getBoolean(KEY_FAST_NONDOMINATED_SORTING, false);
	}
	
	/**
	 * Returns the delta applied to the nadir point of the reference set when 
	 * calculating the hypervolume.  Having a non-zero delta is necessary to 
	 * ensure extremal solutions contribute to the hypervolume.
	 * 
	 * @return the delta applied to the nadir point of the reference set when 
	 *         calculating the hypervolume
	 */
	public static double getHypervolumeDelta() {
		return PROPERTIES.getDouble(KEY_HYPERVOLUME_DELTA, 0.0);
	}
	
	/**
	 * Returns the native hypervolume command; or {@code null} if the default
	 * hypervolume implementation is used.  The default hypervolume 
	 * implementation may become computationally prohibitive on large 
	 * approximation sets or at high dimensions.  The following variable 
	 * substitutions are provided:
	 * <ul>
	 *   <li>{0} number of objectives
	 *   <li>{1} approximation set size
	 *   <li>{2} file containing the approximation set
	 *   <li>{3} file containing the reference point
	 * </ul>
	 *   
	 * @return the native hypervolume command; or {@code null} if the default
	 *         hypervolume implementation is used
	 */
	public static String getHypervolume() {
		return PROPERTIES.getString(KEY_HYPERVOLUME, null);
	}
	
	/**
	 * Returns {@code true} if the approximation set is inverted prior to being
	 * passed to the custom hypervolume implementation; otherwise {@code false}.
	 * 
	 * @return {@code true} if the approximation set is inverted prior to being
	 *         passed to the custom hypervolume implementation; otherwise
	 *         {@code false}
	 */
	public static boolean isHypervolumeInverted() {
		return PROPERTIES.getBoolean(KEY_HYPERVOLUME_INVERTED, false);
	}
	
	/**
	 * Returns {@code true} if hypervolume calculation is enabled; {@code false}
	 * otherwise.  When disabled, the hypervolume should be reported as
	 * {@code Double.NaN}.  Direct use of the {@link Hypervolume} class remains
	 * unaffected by this option.
	 * 
	 * @return {@code true} if hypervolume calculation is enabled; {@code false}
	 *         otherwise
	 */
	public static boolean isHypervolumeEnabled() {
		return PROPERTIES.getBoolean(KEY_HYPERVOLUME_ENABLED, true);
	}
	
	/**
	 * Returns the list of available problems.  This allows enumerating
	 * additional problems without the need for defining and registering a 
	 * service provider on the classpath.
	 * 
	 * @return the list of available problems
	 */
	public static String[] getProblems() {
		return PROPERTIES.getStringArray(KEY_PROBLEM_LIST, new String[0]);
	}
	
	/**
	 * Returns the class for the specified problem.
	 * 
	 * @param name the problem name
	 * @return the class for the specified problem
	 */
	public static String getProblemClass(String name) {
		return PROPERTIES.getString(createKey(KEY_PROBLEM_PREFIX, name, "class"), null);
	}
	
	/**
	 * Returns the reference set filename for the specified problem.
	 * 
	 * @param name the problem name
	 * @return the reference set filename for the specified problem
	 */
	public static String getProblemReferenceSet(String name) {
		return PROPERTIES.getString(createKey(KEY_PROBLEM_PREFIX, name, "referenceSet"), null);
	}
	
	/**
	 * Returns the list of available PISA selectors.
	 * 
	 * @return the list of available PISA selectors
	 */
	public static String[] getPISAAlgorithms() {
		return PROPERTIES.getStringArray(KEY_PISA_ALGORITHMS, new String[0]);
	}
	
	/**
	 * Returns the poll rate, in milliseconds, for how often PISA checks the
	 * state file.
	 * 
	 * @return the poll rate, in milliseconds, for how often PISA checks the
	 *         state file
	 */
	public static int getPISAPollRate() {
		return PROPERTIES.getInt(KEY_PISA_POLL, 100);
	}
	
	/**
	 * Returns the command, invokable through {@link Runtime#exec(String)}, for
	 * starting the PISA selector.
	 * 
	 * @param algorithmName the name of the PISA selector
	 * @return the command, invokable through {@link Runtime#exec(String)}, for
	 *         starting the PISA selector
	 */
	public static String getPISACommand(String algorithmName) {
		return PROPERTIES.getString(createKey(KEY_PISA_PREFIX, algorithmName, "command"), null);
	}
	
	/**
	 * Returns the configuration file for the PISA selector.
	 * 
	 * @param algorithmName the name of the PISA selector
	 * @return the configuration file for the PISA selector
	 */
	public static String getPISAConfiguration(String algorithmName) {
		return PROPERTIES.getString(createKey(KEY_PISA_PREFIX, algorithmName, "configuration"), null);
	}
	
	/**
	 * Returns the list of parameter names for the PISA selector.
	 * 
	 * @param algorithmName the name of the PISA selector
	 * @return the list of parameter names for the PISA selector
	 */
	public static String[] getPISAParameters(String algorithmName) {
		return PROPERTIES.getStringArray(createKey(KEY_PISA_PREFIX, algorithmName, "parameters"), new String[0]);
	}
	
	/**
	 * Returns the default value of the specified parameter for the PISA
	 * selector.
	 * 
	 * @param algorithmName the name of the PISA selector
	 * @param parameterName the name of the parameter
	 * @return the default value of the specified parameter for the PISA
	 *         selector
	 */
	public static String getPISAParameterDefaultValue(String algorithmName, String parameterName) {
		return PROPERTIES.getString(createKey(KEY_PISA_PREFIX, algorithmName, "parameter", parameterName), null);
	}
	
	/**
	 * Returns the list of algorithms displayed in the diagnostic tool GUI.
	 * 
	 * @return the list of algorithms displayed in the diagnostic tool GUI
	 */
	public static String[] getDiagnosticToolAlgorithms() {
		return PROPERTIES.getStringArray(KEY_DIAGNOSTIC_TOOL_ALGORITHMS, new String[] {
				"NSGAII", "NSGAIII", "GDE3", "eMOEA", "eNSGAII", 
				"MOEAD", "MSOPS", "CMA-ES", "SPEA2", "PAES", "PESA2", "OMOPSO",
				"SMPSO", "IBEA", "SMS-EMOA", "VEGA", "DBEA", "Random", "RVEA",
				"RSO", "AMOSA", "GeneticAlgorithm", "DifferentialEvolution", "EvolutionStrategy",
				"SimulatedAnnealing" });
	}
	
	/**
	 * Returns the list of problems displayed in the diagnostic tool GUI.
	 * 
	 * @return the list of problems displayed in the diagnostic tool GUI
	 */
	public static String[] getDiagnosticToolProblems() {
		return PROPERTIES.getStringArray(KEY_DIAGNOSTIC_TOOL_PROBLEMS, new String[] { 
				"DTLZ1_2", "DTLZ2_2", "DTLZ3_2", "DTLZ4_2", "DTLZ7_2", 
				"ROT_DTLZ1_2", "ROT_DTLZ2_2", "ROT_DTLZ3_2", "ROT_DTLZ4_2", "ROT_DTLZ7_2", 
				"UF1", "UF2", "UF3", "UF4", "UF5", "UF6", "UF7", "UF8", "UF9", "UF10", "UF11", "UF12", "UF13",
				"CF1", "CF2", "CF3", "CF4", "CF5", "CF6", "CF7", "CF8", "CF9", "CF10",
				"LZ1", "LZ2", "LZ3", "LZ4", "LZ5", "LZ6", "LZ7", "LZ8", "LZ9",
				"WFG1_2", "WFG2_2", "WFG3_2", "WFG4_2", "WFG5_2", "WFG6_2", "WFG7_2", "WFG8_2", "WFG9_2",
				"ZDT1", "ZDT2", "ZDT3", "ZDT4", "ZDT5", "ZDT6",
				"Belegundu", "Binh", "Binh2", "Binh3", "Binh4", "Fonseca", 
				"Fonseca2", "Jimenez", "Kita", "Kursawe", "Laumanns", "Lis", 
				"Murata", "Obayashi", "OKA1", "OKA2", "Osyczka", "Osyczka2", 
				"Poloni", "Quagliarella", "Rendon", "Rendon2", "Schaffer", 
				"Schaffer2", "Srinivas", "Tamaki", "Tanaka", "Viennet", 
				"Viennet2", "Viennet3", "Viennet4"});
	}
	
	/**
	 * Splits an executable command into its individual arguments.  This method
	 * allows quoted text ({@code "..."}) in properties to be treated as an
	 * individual argument as required by {@link ProcessBuilder}.
	 *  
	 * @param command the command represented in a single string
	 * @return the individual arguments comprising the command
	 */
	public static String[] parseCommand(String command) {
		return new StringTokenizer(command).setQuoteChar('\"').getTokenArray();
	}
	
	/**
	 * Returns {@code true} if genetic programming functions should use
	 * protection against invalid arguments that would otherwise result in
	 * {@code NaN} or other invalid values; {@code false} otherwise.
	 * 
	 * @return {@code true} if genetic programming functions should use
	 *         protection against invalid arguments that would otherwise result
	 *         in {@code NaN} or other invalid values; {@code false} otherwise
	 */
	public static boolean isProtectedFunctions() {
		return PROPERTIES.getBoolean(KEY_GP_PROTECTED_FUNCTIONS, true);
	}
	
	/**
	 * Returns the cleanup strategy when restarting from a previous run.
	 * Possible values are {@code error}, {@code overwrite}, and
	 * {@code restore}.  The default is {@code error}.  Any other values should
	 * default to {@code error}.
	 * 
	 * @return the cleanup strategy when restarting from a previous run
	 */
	public static String getCleanupStrategy() {
		return PROPERTIES.getString(KEY_CLEANUP_STRATEGY, "error");
	}
	
	/**
	 * Returns {@code true} if debugging is enabled when running external
	 * problems.
	 * 
	 * @return {@code true} if debugging for external problems is enabled;
	 *         {@code false} otherwise
	 */
	public static boolean getExternalProblemDebuggingEnabled() {
		return PROPERTIES.getBoolean(KEY_EXTERNAL_PROBLEM_DEBUGGING, false);
	}
	
	/**
	 * Returns the MOEA Framework icons of various sizes.
	 * 
	 * @return the MOEA Framework icons
	 */
	public static List<Image> getIconImages() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		List<Image> icons = new ArrayList<Image>();
		
		icons.add(toolkit.getImage(Settings.class.getResource("logo16.png")));
		icons.add(toolkit.getImage(Settings.class.getResource("logo24.png")));
		icons.add(toolkit.getImage(Settings.class.getResource("logo32.png")));
		icons.add(toolkit.getImage(Settings.class.getResource("logo48.png")));
		icons.add(toolkit.getImage(Settings.class.getResource("logo64.png")));
		icons.add(toolkit.getImage(Settings.class.getResource("logo128.png")));
		icons.add(toolkit.getImage(Settings.class.getResource("logo256.png")));

		return icons;
	}
	
	/**
	 * Creates the key for a property by concatenating an optional prefix with one or more parts.
	 * 
	 * @param prefix the prefix or first part of the key
	 * @param parts remaining parts of the key
	 * @return the full key
	 */
	public static String createKey(String prefix, String... parts) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(prefix, 0, prefix.endsWith(".") ? prefix.length()-1 : prefix.length());
		
		for (String part : parts) {
			sb.append(".");
			sb.append(part);
		}
		
		return sb.toString();
	}
	
}

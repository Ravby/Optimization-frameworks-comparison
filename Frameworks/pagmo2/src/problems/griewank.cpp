/* Copyright 2017-2021 PaGMO development team

This file is part of the PaGMO library.

The PaGMO library is free software; you can redistribute it and/or modify
it under the terms of either:

  * the GNU Lesser General Public License as published by the Free
    Software Foundation; either version 3 of the License, or (at your
    option) any later version.

or

  * the GNU General Public License as published by the Free Software
    Foundation; either version 3 of the License, or (at your option) any
    later version.

or both in parallel, as here.

The PaGMO library is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
for more details.

You should have received copies of the GNU General Public License and the
GNU Lesser General Public License along with the PaGMO library.  If not,
see https://www.gnu.org/licenses/. */

#include <cmath>
#include <initializer_list>
#include <stdexcept>
#include <string>
#include <utility>

#include <pagmo/exceptions.hpp>
#include <pagmo/problem.hpp>
#include <pagmo/problems/griewank.hpp>
#include <pagmo/s11n.hpp>
#include <pagmo/types.hpp>
#include <pagmo/problems/fitness_tracker.hpp>

namespace pagmo
{
static const double griewankShifted[]
    = {104.94691081342978,  -135.6761348470227,  92.81100891955543,   -10.87108518188711, 407.63013054172563,
       -223.31315231746964, -224.56627659185563, -222.7632371161696,  -338.7309010063917, -236.65660889064418,
       -102.44810336184622, 126.42998956660733,  145.51932139597716,  362.4195007946686,  -136.07822974158148,
       385.3787391034516,   224.54916653990915,  43.478124239142176,  174.10299590196337, -214.8310656032395,
       -398.01422639093084, -309.2614310193993,  -178.06570153291875, -162.6651823263993, 450.850657541738,
       -454.92932203096916, -419.61768171271166, -297.80826342133184, 66.9431726795051,   81.54612611060406,
       33.75350391540678,   452.7503721088576,   -394.37679317004665, 211.96497077719948, 209.2762825707889,
       -456.2796841185877,  43.786866583660526,  39.94139518856173,   -392.0757065399322, 368.3834199650955,
       -230.1695108177757,  260.9386598025741,   424.9982235544909,   219.5539088594145,  -56.74195565523951,
       353.31823225966764,  -314.409074063865,   -344.22129416136335, 350.389300480917,   210.6305580593255,
       -351.3688705488921,  -419.56080494622336, -352.4149162964608,  -306.0148711883166, 234.1092941008617,
       179.7062426106279,   -180.45299833941465, -353.7895714765393,  304.63535113945875, 11.040575864524044,
       409.40525097198395,  -417.0405885951272,  460.81384815623824,  476.25681692986484, 248.03325986310347,
       126.13612920930825,  366.9901574947629,   40.28445536257732,   196.48680138749523, -89.56987522823874,
       -31.05376733595574,  -254.8501756921979,  341.9328159488531,   -366.2937316074139, -98.73389669853748,
       -318.98905799691175, -152.38232167124994, 367.26470889921757,  -72.94267032929105, 372.16601829879653,
       96.98492379728134,   -385.9422293412532,  -94.28273010579477,  346.5708159613322,  -139.0701306975041,
       -62.71021784084701,  -477.67009305148014, 267.4589914695547,   135.44409046482406, -107.786912911326,
       12.944227804931472,  452.8144059395564,   -2.2036047536961973, -458.0828323445677, -305.00083158848463,
       352.90943721979033,  -173.23435776482415, -294.46059074937193, 329.82221519045083, 295.09636446279023};


griewank::griewank(unsigned dim) : m_dim(dim)
{
    if (dim < 1u) {
        pagmo_throw(std::invalid_argument,
                    "Griewank Function must have minimum 1 dimension, " + std::to_string(dim) + " requested");
    }
}

/// Fitness computation
/**
 * Computes the fitness for this UDP
 *
 * @param x the decision vector.
 *
 * @return the fitness of \p x.
 */
vector_double griewank::fitness(const vector_double &x) const
{
    vector_double f(1, 0.);
    auto n = x.size();
    double fr = 4000.;
    double retval = 0.;
    double p = 1.;

    vector_double shifted(n);
    for (int i = 0; i < n; ++i) {
        shifted[i] = x[i] - griewankShifted[i];
    }

    for (decltype(n) i = 0u; i < n; i++) {
        retval += shifted[i] * shifted[i];
    }
    for (decltype(n) i = 0u; i < n; i++) {
        p *= std::cos(shifted[i] / std::sqrt(static_cast<double>(i) + 1.0));
    }
    f[0] = (retval / fr - p + 1.);

    FitnessTracker::addFitness(f[0]);

    return f;
}

/// Box-bounds
/**
 * It returns the box-bounds for this UDP.
 *
 * @return the lower and upper bounds for each of the decision vector components
 */
std::pair<vector_double, vector_double> griewank::get_bounds() const
{
    vector_double lb(m_dim, -600);
    vector_double ub(m_dim, 600);
    return {lb, ub};
}

/// Optimal solution
/**
 * @return the decision vector corresponding to the best solution for this problem.
 */
vector_double griewank::best_known() const
{
    return vector_double(m_dim, 0.);
}

// Object serialization
template <typename Archive>
void griewank::serialize(Archive &ar, unsigned)
{
    ar &m_dim;
}

} // namespace pagmo

PAGMO_S11N_PROBLEM_IMPLEMENT(pagmo::griewank)

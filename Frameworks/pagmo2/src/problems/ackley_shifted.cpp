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

#include <pagmo/detail/constants.hpp>
#include <pagmo/exceptions.hpp>
#include <pagmo/problem.hpp>
#include <pagmo/problems/ackley_shifted.hpp>
#include <pagmo/s11n.hpp>
#include <pagmo/types.hpp>
#include <pagmo/problems/fitness_tracker.hpp>

namespace pagmo
{
static const double shiftedAckley[]
    = {21.125524221556333,  -11.11861062406522,  -8.384759722218377,   -4.998410532366073,  15.080716791129063,
       17.219200462474568,  17.30816180946608,   10.917710600242835,   20.963400683396372,  19.33124762324178,
       -10.07785283476705,  -0.6470251048379687, -9.17403575481461,    0.2263125149343992,  3.5360559029458436,
       -24.976221736741277, 5.4509130845083185,  -22.008998257857435,  4.319939429353628,   -0.015227633728962076,
       -10.266583117363774, -2.984262944040246,  -14.575704508621596,  9.332038871399178,   12.260212736282845,
       2.6286817951067007,  3.7062873124753715,  -7.422751349625646,   22.839645124520274,  18.139957746273467,
       -23.12538796733297,  3.106978031277709,   -21.8650793129579,    23.285704349592457,  -8.877654886311145,
       -15.936478675865914, -14.529257223964459, -0.5256354355698463,  3.421693809849465,   -22.925462999946205,
       -13.789171558857868, -12.260934479396866, 0.8518120279262078,   -6.2876202233673375, 17.750101847964885,
       24.91013310009094,   -3.1246259173628452, -7.631501678844927,   -7.124798758640981,  0.9420940535559126,
       -3.5298708787570163, -13.655076475547936, -3.220475098055129,   -0.7478333903264627, 13.62921655558997,
       -3.336289145576181,  -1.7729883120516092, -2.487660518823951,   13.831869546225832,  3.8314346375240227,
       -17.39761276709943,  10.865095694434025,  15.228433667840704,   -16.627545155754365, -20.431272665369136,
       -6.458592974832296,  7.631660014538554,   3.074842377520376,    -10.126681052067994, 5.3638689257400145,
       21.993909890455164,  9.497579963476142,   -25.38346714493473,   23.309384518115635,  3.51234224076034,
       22.789117409253414,  17.335098841147875,  16.853847369236334,   -22.711831662040623, -21.43493872394238,
       14.12580762325981,   12.670980727215792,  -0.18261513276357988, -2.756069406807832,  -13.440337358321415,
       -7.500233746130899,  -22.829280727381548, 22.39647155450566,    -9.793500872001813,  -11.480708343814184,
       -11.497396176336123, -17.84442861516022,  16.259172390942574,   -4.7388363410613366, -22.313184156325793,
       -16.909573209790985, 21.735083164944925,  -13.918617730409967,  -21.395853820675416, -22.481403053600452};

ackleyShifted::ackleyShifted(unsigned dim) : m_dim(dim)
{
    if (dim < 1u) {
        pagmo_throw(std::invalid_argument,
                    "ackleyShifted Function must have minimum 1 dimension, " + std::to_string(dim) + " requested");
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
vector_double ackleyShifted::fitness(const vector_double &x) const
{
    vector_double f(1, 0.);
    auto n = x.size();
    double omega = 2. * detail::pi();
    double s1 = 0., s2 = 0.;
    double nepero = std::exp(1.0);

    vector_double shifted(n);
    for (int i = 0; i < n; ++i) {
        shifted[i] = x[i] - shiftedAckley[i];
    }

    for (decltype(n) i = 0u; i < n; i++) {
        s1 += shifted[i] * shifted[i];
        s2 += std::cos(omega * shifted[i]);
    }
    f[0] = -20 * std::exp(-0.2 * std::sqrt(1.0 / static_cast<double>(n) * s1))
           - std::exp(1.0 / static_cast<double>(n) * s2) + 20 + nepero;

    FitnessTracker::addFitness(f[0]);

    return f;
}

/// Box-bounds
/**
 *
 * It returns the box-bounds for this UDP.
 *
 * @return the lower and upper bounds for each of the decision vector components
 */
std::pair<vector_double, vector_double> ackleyShifted::get_bounds() const
{
    vector_double lb(m_dim, -32);
    vector_double ub(m_dim, 32);
    return {lb, ub};
}

/// Optimal solution
/**
 * @return the decision vector corresponding to the best solution for this problem.
 */
vector_double ackleyShifted::best_known() const
{
    return vector_double(m_dim, 0.);
}

// Object serialization
template <typename Archive>
void ackleyShifted::serialize(Archive &ar, unsigned)
{
    ar &m_dim;
}

} // namespace pagmo

PAGMO_S11N_PROBLEM_IMPLEMENT(pagmo::ackleyShifted)

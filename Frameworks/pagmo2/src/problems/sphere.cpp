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
#include <pagmo/problems/sphere.hpp>
#include <pagmo/s11n.hpp>
#include <pagmo/types.hpp>

namespace pagmo
{

static const double sphereShifted[]
    = {45.00458616074768,   -50.34195790924958,  16.656571994777153,  -70.66157384610746,  77.87240204480926,
       41.15636478376551,   49.128266155105166,  -60.307473748449034, 33.10423742373693,   0.6236286355758835,
       27.460586290936902,  -36.02013445020688,  -40.00877054048374,  -64.66271141874185,  68.90566619452034,
       62.70237277088032,   -4.074486986783384,  -1.317873518733137,  -55.54285608300457,  -47.34319489184719,
       12.507912147062243,  -40.535720288582304, 33.31036155896686,   -28.838912135698926, -1.4693369446360407,
       -10.418532078512769, 73.36998380030019,   -21.21493655887511,  44.00620956459555,   53.63261051536182,
       2.7285937751837395,  13.470863898732262,  18.406813289986204,  79.81576486014623,   11.44319844208276,
       54.93447134455431,   57.30432717171027,   40.23921236138773,   65.67885067903487,   26.366481706628406,
       -27.30759152512293,  25.811669049736366,  33.765769465674026,  46.064188205488364,  68.68320146596031,
       -30.14247692338735,  -20.27099815222924,  72.28995446564878,   -23.225596585148693, -17.707777462132427,
       -16.533526290183076, 52.71601762751186,   -34.19215602995044,  17.794220792755056,  62.28063781336567,
       48.17997449949212,   -62.87666397080855,  -0.3946724203053975, -63.61229513391514,  62.025414757794124,
       -53.919013466682095, 22.71650382909786,   -48.50198607380415,  39.16369296912181,   -33.30665184747603,
       69.78065114460998,   44.39743053916996,   -79.65007815070732,  4.313169443398394,   -38.431776573060716,
       -3.2886541591535376, -4.464771381506893,  -11.851966954379932, 53.314696580174,     -47.80425745244058,
       -28.361356211321436, 46.48058986649623,   24.913829708880712,  32.701013472334154,  -16.81491036111501,
       -74.32158198349097,  62.781854475534715,  -39.33464299745868,  71.914280371025,     43.45306457719519,
       45.34911724499962,   17.635557921803695,  42.528929208317535,  57.38289380412539,   -59.172599789657966,
       68.02685304924859,   -22.83947203810746,  61.909059219612146,  -25.281987529689864, -39.32991901928908,
       21.52408688800091,   19.141746734135268,  33.404708887073156,  -40.93749669488551,  -24.985279381108313};

sphere::sphere(unsigned dim) : m_dim(dim)
{
    if (dim < 1u) {
        pagmo_throw(std::invalid_argument,
                    "Sphere Function must have minimum 1 dimension, " + std::to_string(dim) + " requested");
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
vector_double sphere::fitness(const vector_double &x) const
{
    vector_double f(1, 0.);
    auto n = x.size();

    vector_double shifted(n);
    for (int i = 0; i < n; ++i) {
		shifted[i] = x[i] - sphereShifted[i];
	}

    for (decltype(n) i = 0u; i < n; i++) {
        f[0] += shifted[i] * shifted[i];
    }

    return f;
}

/// Box-bounds
/**
 *
 * It returns the box-bounds for this UDP.
 *
 * @return the lower and upper bounds for each of the decision vector components
 */
std::pair<vector_double, vector_double> sphere::get_bounds() const
{
    vector_double lb(m_dim, -100);
    vector_double ub(m_dim, 100);
    return {lb, ub};
}

/// Optimal solution
/**
 * @return the decision vector corresponding to the best solution for this problem.
 */
vector_double sphere::best_known() const
{
    return vector_double(m_dim, 0.);
}

// Object serialization
template <typename Archive>
void sphere::serialize(Archive &ar, unsigned)
{
    ar &m_dim;
}

} // namespace pagmo

PAGMO_S11N_PROBLEM_IMPLEMENT(pagmo::sphere)

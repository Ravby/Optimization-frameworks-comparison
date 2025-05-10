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
#include <pagmo/problems/schwefel.hpp>
#include <pagmo/s11n.hpp>
#include <pagmo/types.hpp>

namespace pagmo
{

static const double schwefelShifted[]
    = {65.90306349157711,   7.223609380925922,   48.06403595428989,   -61.23008904674027,  -56.996531732952235,
       -65.67006959079087,  56.81852866354663,   -49.51353093024842,  -42.403457350576694, 64.22460834371887,
       -15.782490031817943, 78.2275393766464,    58.981759831549084,  -41.74229801452056,  70.70390026719807,
       -27.120714671264494, -43.362534341811525, -11.357893018802812, 25.329639322623194,  -24.02213869456814,
       -29.51839742945309,  -50.65352492132828,  -17.33907934005014,  -75.7276769694174,   25.60834507308691,
       -40.61133175959546,  53.35222265494423,   30.414516938139826,  -37.83651132063628,  30.243572616044418,
       -2.2080543227080227, -51.34193306616531,  37.36039082956739,   -44.93167877354562,  63.19570228206453,
       -0.3230550173947364, 54.997116479336086,  -39.773290218583256, 54.1565458883382,    12.124637138785076,
       43.85247188324382,   -75.91826727683562,  49.74255951193999,   63.92380934836413,   54.34684698334098,
       -19.563396930885908, 31.656857648987852,  -32.907795698796306, -29.963471074339374, -60.672896416251675,
       -77.62660870400686,  -75.95478227792732,  23.6157226667015,    75.57747129002121,   -15.915369172179027,
       -45.240636434786694, 34.3519391836095,    65.07767014581174,   13.32242757194949,   71.34237817983137,
       -74.51968139294803,  3.512587033090128,   56.30141471139396,   31.538703365256055,  24.585815604555407,
       -67.48845399940869,  -40.64252673369548,  59.91338156677921,   -74.95426285566866,  -38.150045876281204,
       -43.153328841782056, 21.868730315180528,  -41.62610523688311,  1.6077581521206525,  -72.85317012180792,
       63.05034100312395,   48.208504063943536,  -19.726627265139726, -14.832261021455807, -5.053624253018285,
       -60.42559524043723,  -53.2642183230471,   -48.14019430704212,  35.20030522956961,   79.05287296526038,
       -79.9325666407405,   59.03982443403498,   53.3011236362469,    20.874500877568096,  44.9656312058855,
       74.28787167461365,   44.31739930419482,   -78.40723117562789,  -54.82586056439315,  -36.33222788844623,
       69.06988428571137,   79.06863059997934,   22.41510141643954,   61.96066562216663,   -5.6050556467548205};

schwefel::schwefel(unsigned dim) : m_dim(dim)
{
    if (dim < 1u) {
        pagmo_throw(std::invalid_argument,
                    "Schwefel Function must have minimum 1 dimension, " + std::to_string(dim) + " requested");
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
vector_double schwefel::fitness(const vector_double &x) const
{
    vector_double f(1, 0.);
    auto n = x.size();
    double sum = 0.;

     vector_double shifted(n);
    for (int i = 0; i < n; ++i) {
        shifted[i] = x[i] - schwefelShifted[i];
    }

    for (decltype(n) i = 0u; i < n; i++) {
        for(decltype(n) j = 0u; j <= i; j++){
            sum += shifted[i];
        }
        f[0] += sum * sum;
    }

    return f;
}

/// Box-bounds
/**
 * It returns the box-bounds for this UDP.
 *
 * @return the lower and upper bounds for each of the decision vector components
 */
std::pair<vector_double, vector_double> schwefel::get_bounds() const
{
    vector_double lb(m_dim, -100);
    vector_double ub(m_dim, 100);
    return {lb, ub};
}

/// Optimal solution
/**
 * @return the decision vector corresponding to the best solution for this problem.
 */
vector_double schwefel::best_known() const
{
    return vector_double(m_dim, 0.);
}

// Object serialization
template <typename Archive>
void schwefel::serialize(Archive &ar, unsigned)
{
    ar &m_dim;
}

} // namespace pagmo

PAGMO_S11N_PROBLEM_IMPLEMENT(pagmo::schwefel)

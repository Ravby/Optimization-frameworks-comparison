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
#include <pagmo/problems/shekel_fox_holes.hpp>
#include <pagmo/s11n.hpp>
#include <pagmo/types.hpp>

namespace pagmo
{

shekelsFoxHoles::shekelsFoxHoles(unsigned dim) : m_dim(dim)
{
    if (dim < 1u) {
        pagmo_throw(std::invalid_argument,
                    "ShekelsFoxHoles Function must have minimum 1 dimension, " + std::to_string(dim) + " requested");
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
vector_double shekelsFoxHoles::fitness(const vector_double &x) const
{
    vector_double f(1, 0.);
    auto n = x.size();
    double sum;
    for (decltype(n) j = 0u; j < 25u; j++) {
        sum = 0;
        for (decltype(n) i = 0u; i < n; i++) {
            sum += pow(x[i] - a[j][i], 6);
        }
        sum += j + 1;
        f[0] += 1.0 / sum;
    }
    f[0] += 1.0 / 500.0;
    f[0] = pow(f[0], -1);
    return f;
}

/// Box-bounds
/**
 * It returns the box-bounds for this UDP.
 *
 * @return the lower and upper bounds for each of the decision vector components
 */
std::pair<vector_double, vector_double> shekelsFoxHoles::get_bounds() const
{
    vector_double lb(m_dim, -65.536);
    vector_double ub(m_dim, 65.536);
    return {lb, ub};
}

/// Optimal solution
/**
 * @return the decision vector corresponding to the best solution for this problem.
 */
vector_double shekelsFoxHoles::best_known() const
{
    return {vector_double(m_dim, (31.97833 * -1.))};
}

// Object serialization
template <typename Archive>
void shekelsFoxHoles::serialize(Archive &ar, unsigned)
{
    ar &m_dim;
}

} // namespace pagmo

PAGMO_S11N_PROBLEM_IMPLEMENT(pagmo::shekelsFoxHoles)

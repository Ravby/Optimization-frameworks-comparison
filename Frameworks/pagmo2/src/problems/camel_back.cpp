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
#include <pagmo/problems/camel_back.hpp>
#include <pagmo/s11n.hpp>
#include <pagmo/types.hpp>

namespace pagmo
{

camelBack::camelBack(unsigned dim) : m_dim(dim)
{
    if (dim < 1u) {
        pagmo_throw(std::invalid_argument,
                    "CamelBack Function must have minimum 1 dimension, " + std::to_string(dim) + " requested");
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
vector_double camelBack::fitness(const vector_double &x) const
{
    vector_double f(1, 0.);
    auto n = x.size();

    f[0] = 4 * pow(x[0], 2)
           - 2.1 * pow(x[0], 4)
           + (1.0 / 3.0) * pow(x[0], 6)
           + x[0] * x[1]
           - 4 * pow(x[1], 2)
           + 4 * pow(x[1], 4);

    return f;

}

/// Box-bounds
/**
 *
 * It returns the box-bounds for this UDP.
 *
 * @return the lower and upper bounds for each of the decision vector components
 */
std::pair<vector_double, vector_double> camelBack::get_bounds() const
{
    vector_double lb(m_dim, -5.);
    vector_double ub(m_dim, 5.);
    return {lb, ub};
}

/// Optimal solution
/**
 * @return the decision vector corresponding to the best solution for this problem.
 */
vector_double camelBack::best_known() const
{
    return {0.089842014, -0.7126564033};
}

// Object serialization
template <typename Archive>
void camelBack::serialize(Archive &ar, unsigned)
{
    ar &m_dim;
}

} // namespace pagmo

PAGMO_S11N_PROBLEM_IMPLEMENT(pagmo::camelBack)
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

#ifndef PAGMO_PROBLEMS_HARTMAN_HPP
#define PAGMO_PROBLEMS_HARTMAN_HPP

#include <string>
#include <utility>

#include <pagmo/detail/visibility.hpp>
#include <pagmo/problem.hpp>
#include <pagmo/s11n.hpp>
#include <pagmo/types.hpp>

namespace pagmo
{

/// The Hartman problem.
/**
 *
 * \image html Hartman.png "Two-dimensional Hartman function." width=3cm
 *
 * This is a scalable box-constrained continuous single-objective problem.
 * The objective function is the generalised n-dimensional Hartman function:
 */

struct PAGMO_DLL_PUBLIC hartman {
    /// Constructor from dimension
    /**
     * Constructs an Hartman problem
     *
     * @param dim the problem dimensions.
     *
     * @throw std::invalid_argument if \p dim is < 1
     */
    hartman(unsigned dim = 1u);
    // Fitness computation
    vector_double fitness(const vector_double &) const;
    // Box-bounds
    std::pair<vector_double, vector_double> get_bounds() const;
    /// Problem name
    /**
     * @return a string containing the problem name
     */
    std::string get_name() const
    {
        return "Hartman Function";
    }
    // Optimal solution
    vector_double best_known() const;
    /// Problem dimensions
    unsigned m_dim;

private:
    // Object serialization
    friend class boost::serialization::access;
    template <typename Archive>
    void serialize(Archive &, unsigned);

    std::vector<vector_double> a = {
        {3, 10, 30},
        {0.1, 10, 35},
        {3, 10, 30},
        {0.1, 10, 35}
    };
    std::vector<vector_double> p
        = {
        {0.3689, 0.1170, 0.2673},
        {0.4699, 0.4387, 0.7470},
        {0.1091, 0.8732, 0.5547},
        {0.03815, 0.5743, 0.8828}
    };

    vector_double c = {1, 1.2, 3, 3.2};
};

} // namespace pagmo

PAGMO_S11N_PROBLEM_EXPORT_KEY(pagmo::hartman)

#endif

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
#include <vector>

#include <pagmo/detail/constants.hpp>
#include <pagmo/exceptions.hpp>
#include <pagmo/problem.hpp>
#include <pagmo/problems/rastrigin.hpp>
#include <pagmo/s11n.hpp>
#include <pagmo/types.hpp>
#include <pagmo/problems/fitness_tracker.hpp>

namespace pagmo
{

static const double rastriginShifted[]
    = {4.070088073870897,   -3.3584914395112166,    2.5276924556298725,    1.373781594176445,    -1.8387129521132595,
       2.057549949794417,   -0.0023364913111585395, 2.013487904123477,     -3.7248964059839147,  -0.33919629789907413,
       2.8301821463273367,  -2.2929959706368352,    -2.2706325455604484,   -3.513692538288365,   -1.523336663056385,
       -4.0764777207287075, -1.6237586256460155,    0.9178097684770039,    -0.2723487256238868,  -2.9841270159498463,
       2.9288432571682828,  0.22934413055341984,    3.720378352924264,     -1.429905611686805,   1.6068438224463835,
       2.1513735804078467,  -0.6971225700819095,    -2.8180867567781878,   -1.2171333029086893,  -4.064563849878518,
       1.3543350787859474,  2.4050645503835417,     -2.5584324886855985,   1.5660383241939355,   2.1289004812453385,
       3.8337296275949706,  -0.5606593824135673,    1.141577243686811,     0.8120054159429309,   0.5394159295625087,
       -2.5598649074502093, 4.033201745873486,      3.24227861085845,      2.916933432390337,    3.639752419533484,
       1.5188619717725969,  3.839178330380485,      3.975865667365813,     -1.191858979126291,   -3.489747339689812,
       3.6319133591436685,  -0.8694397826397151,    -0.039238131176527524, 0.857258173067124,    3.5742721242496724,
       -1.2964486629139182, 0.622681353542129,      -3.2417846861744932,   -3.5847655715322952,  -0.20674408185538518,
       -1.4176918157719318, -1.8093890874563168,    -2.4458826348346667,   -2.4951135809966636,  -0.3701415900112224,
       -0.9412743172996478, -2.5093000736061777,    -0.957089975451122,    -0.34490389779906305, -0.9080868663263582,
       2.5324398853872463,  0.7681006679604776,     0.0686178202466019,    3.3249963877119377,   -1.7808876498791788,
       -2.765311233368114,  -4.021295100468257,     -3.0287066679231973,   1.1237366969449676,   2.593350377277937,
       -2.882030611770738,  -1.0382816516673712,    -2.7461834729291623,   -3.886857036748543,   2.3862007049941916,
       1.7060737084162199,  3.0561050423140896,     2.7412547729688193,    3.6348483498236694,   2.0446951025524065,
       -2.1226445447582245, 2.2055941964197308,     -1.881997196954174,    -2.0742308641271237,  -4.0625624578635255,
       -3.0987340790486426, 2.5444665506465656,     -1.6879107868549954,   -2.299820194854612,   -0.6872363415421097};

rastrigin::rastrigin(unsigned dim) : m_dim(dim)
{
    if (dim < 1u) {
        pagmo_throw(std::invalid_argument,
                    "Rastrigin Function must have minimum 1 dimension, " + std::to_string(dim) + " requested");
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
vector_double rastrigin::fitness(const vector_double &x) const
{
    vector_double f(1, 0.);
    const auto omega = 2. * pagmo::detail::pi();
    auto n = x.size();

    vector_double shifted(n);
    for (int i = 0; i < n; ++i) {
        shifted[i] = x[i] - rastriginShifted[i];
    }

    for (decltype(n) i = 0u; i < n; ++i) {
        f[0] += shifted[i] * shifted[i] - 10. * std::cos(omega * shifted[i]);
    }
    f[0] += 10. * static_cast<double>(n);

    FitnessTracker::addFitness(f[0]);

    return f;
}

/// Box-bounds
/**
 * It returns the box-bounds for this UDP.
 *
 * @return the lower and upper bounds for each of the decision vector components
 */
std::pair<vector_double, vector_double> rastrigin::get_bounds() const
{
    vector_double lb(m_dim, -5.12);
    vector_double ub(m_dim, 5.12);
    return {lb, ub};
}

/// Gradients
/**
 * It returns the fitness gradient for this UDP.
 *
 * The gradient is represented in a sparse form as required by
 * problem::gradient().
 *
 * @param x the decision vector.
 *
 * @return the gradient of the fitness function
 */
vector_double rastrigin::gradient(const vector_double &x) const
{
    auto n = x.size();
    vector_double g(n);
    const auto omega = 2. * pagmo::detail::pi();
    for (decltype(n) i = 0u; i < n; ++i) {
        g[i] = 2 * x[i] + 10.0 * omega * std::sin(omega * x[i]);
    }
    return g;
}

/// Hessians
/**
 * It returns the hessians for this UDP.
 *
 * The hessians are represented in a sparse form as required by
 * problem::hessians().
 *
 * @param x the decision vector.
 *
 * @return the hessians of the fitness function
 */
std::vector<vector_double> rastrigin::hessians(const vector_double &x) const
{
    auto n = x.size();
    vector_double h(n);
    const auto omega = 2. * pagmo::detail::pi();
    for (decltype(n) i = 0u; i < n; ++i) {
        h[i] = 2 + 10.0 * omega * omega * std::cos(omega * x[i]);
    }
    return {h};
}

/// Hessians sparsity (only the diagonal elements are non zero)
/**
 * It returns the hessian sparisty structure for this UDP.
 *
 * The hessian sparisty is represented in the form required by
 * problem::hessians_sparsity().
 *
 * @return the hessians of the fitness function
 */
std::vector<sparsity_pattern> rastrigin::hessians_sparsity() const
{
    sparsity_pattern hs;
    auto n = m_dim;
    for (decltype(n) i = 0u; i < n; ++i) {
        hs.push_back({i, i});
    }
    return {hs};
}

/// Optimal solution
/**
 * @return the decision vector corresponding to the best solution for this problem.
 */
vector_double rastrigin::best_known() const
{
    return vector_double(m_dim, 0.);
}

// Object serialization
template <typename Archive>
void rastrigin::serialize(Archive &ar, unsigned)
{
    ar &m_dim;
}

} // namespace pagmo

PAGMO_S11N_PROBLEM_IMPLEMENT(pagmo::rastrigin)

using Microsoft.EntityFrameworkCore;
using Sportivo_webAPI.DAL;
using Sportivo_webAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Sportivo_webAPI.Repositories
{
    public class CompanyRepository
    {
        public ICollection<Company> GetAll()
        {
            try
            {
                using (var context = new SportivoContext(new DbContextOptions<SportivoContext>()))
                {
                    var companies = context.Companies.ToList();
                    return companies;
                }
            }
            catch { return null; }
        }

        public Company Get(int id)
        {
            try
            {
                using (var context = new SportivoContext(new DbContextOptions<SportivoContext>()))
                {
                    var company = context.Companies.FirstOrDefault(c => c.CompanyId == id);
                    return company;
                }
            }
            catch { return null; }
        }

        public bool Add(Company company)
        {
            try
            {
                using (var context = new SportivoContext(new DbContextOptions<SportivoContext>()))
                {
                    context.Companies.Add(company);
                    context.SaveChanges();
                    return true;
                }
            }
            catch { return false; }
        }
        public bool Update(Company company, Company updated)
        {
            try
            {
                using (var context = new SportivoContext(new DbContextOptions<SportivoContext>()))
                {
                    company.CompanyName = updated.CompanyName;
                    company.Description = updated.Description;
                    company.Latitude = updated.Latitude;
                    company.Longitude = updated.Longitude;
                    company.PhoneNumber = updated.PhoneNumber;

                    context.SaveChanges();
                    return true;
                }
            }
            catch { return false; }
        }

        public bool Delete(Company company)
        {
            try
            {
                using (var context = new SportivoContext(new DbContextOptions<SportivoContext>()))
                {
                    context.Companies.Remove(company);
                    return true;
                }
            }
            catch { return false; }
        }
    }
}
